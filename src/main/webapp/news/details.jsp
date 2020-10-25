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
      
        <div id="main">
            <h3>Details de la news ${news.titre}</h3>
            <br><br>
            <table>
            <tr>
                <td><a href="home?id=${news.theme.idTheme}&theme=${news.theme.libelle}&action=2">${news.theme.libelle}</a></td>
            </tr>
            <tr>
                <td><strong>${news.titre}</strong></td>
            </tr>
            <tr>
                <td>${news.content}</td>
            </tr>
            <tr>
                <td>
                <c:forEach var="tag" items="${requestScope.listeTagsForNews}">
                    <span><a href="home?id=${tag.idTag}&action=1">${tag.libelle}</a></span>
                </c:forEach>
                </td>
            </tr>
            </table>
        </div>
        </div>
    </body>
</html>
