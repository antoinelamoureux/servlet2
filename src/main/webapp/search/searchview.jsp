<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">
        <title>Search Results</title>
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
            <h3>Résultats pour ${requestScope.searchQuery}</h3>
            <br>
            <table>
            <c:forEach var="results" items="${requestScope.searchResults}">
            <tr>
                <td><strong>${results.titre}</strong></td>
            </tr>
            <tr>
                <td>${results.content}</td>
            </tr>
            <br><br>
            </tr>
        </c:forEach>
            </table>  
        </div>
    </body>
</html>

