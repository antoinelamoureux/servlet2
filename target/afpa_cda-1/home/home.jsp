<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">
        <title>JSP Page</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <!-- div de centrage -->
        <div>
            
            <!-- fichiers inclus -->
            <jsp:include page="../haut.jsp"/>
            <jsp:include page="../navigation.jsp"/>
            <jsp:include page="../search.jsp"/>
        
        <div id="main">
            <h3>Home</h3>
             <c:if test="${sessionScope.user == true}">
                 <div text-align="center">
                     <p>Bienvenue ${sessionScope.user.firstName} ${sessionScope.user.lastName}</p>
                 </div>
            </c:if> 
            <p class="nouvel-item"><a href="news?action=1"><img src="MEDIAS/ICO_NEW.png" width="100" /></a></p>
            <table>
            <caption>liste des news</caption>
            <tr>
                <th>Thème</th>
                <th>Titre</th>
                <th>Crée le</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>

        <c:forEach var="news" items="${requestScope.listeNews}">
            
            <tr>
                <td>${news.theme.libelle}</td>
                <td>${news.titre}</td>
                <td>${news.dateCreation}</td>
                <td class="special_cell"><a href="news?id=${news.idNews}&action=4"><img src="MEDIAS/tags.jpg"  /></a></td>
                <td><a href="news?id=${news.idNews}&action=2"><img src="MEDIAS/ICO_MODIFY.png"  /></a></td>
                <td><a href="news?id=${news.idNews}&action=3"><img src="MEDIAS/ICO_DELETE.png"  /></a></td>
            </tr>
            
        </c:forEach>
            
        </table>
            <br><br>
        <table>
            <caption>liste des tags</caption>

        <c:forEach var="tag" items="${requestScope.listeTags}">
            
            <tr>
                <td><a href="home?id=${tag.idTag}&action=1">${tag.libelle}</a></td>
                <td><a href="tags?id=${tag.idTag}&action=2"><img src="MEDIAS/ICO_MODIFY.png" width="100" /></a></td>
                <td><a href="tags?id=${tag.idTag}&action=3"><img src="MEDIAS/ICO_DELETE.png" width="100" /></a></td>
            </tr>
            
        </c:forEach>    
        </table>   
        <c:if test="${requestScope.isNewsForTag == true}">
            <br>
            <table>
            <c:forEach var="news" items="${requestScope.listeNewsForTag}">
            <tr>
                <td><strong>${news.titre}</strong></td>
            </tr>
            <tr>
                <td>${news.content}</td>
            </tr>
            <br><br>
            </tr>
        </c:forEach>
            </table>
        </c:if> 
        <c:if test="${requestScope.isNewsForTheme == true}">
            <br>
            <table>
            <tr>
                <td><strong>News pour le thème ${requestScope.theme}</strong></td>
            </tr>
            <c:forEach var="news" items="${requestScope.listeNewsForTheme}">
            <tr>
                <td><strong>${news.titre}</strong></td>
            </tr>
            <tr>
                <td>${news.content}</td>
            </tr>
            <br><br>
            </tr>
        </c:forEach>
            </table>
        </c:if>
        </div>
        </div>
    </body>
</html>
