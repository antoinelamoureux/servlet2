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
             <c:if test="${sessionScope.user != null}">
                 <div text-align="center">
                     <p>Bienvenue ${sessionScope.user.firstName} ${sessionScope.user.lastName}</p>
                 </div>
            </c:if> 
        </div>
    </body>
</html>

