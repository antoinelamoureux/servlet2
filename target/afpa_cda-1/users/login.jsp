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

        <div id="main">
            <form action="users" method="post">
                <input type="hidden" name="action" value="login"/>
                <input type="text" name="username" placeholder="Votre identifiant"/><br>
                <input type="password" name="password" placeholder="Votre mot de passe"/><br>
                <input style="width:150px" type="submit" name="submit">
            </form>
        </div>
    </body>
</html>

