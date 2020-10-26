<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
<nav>
    <a href="home" ><p <c:if test="${requestScope.section == 'SECTION-HOME'}"> class="selected" </c:if> >Home</p></a>
    <a href="news" ><p <c:if test="${requestScope.section == 'SECTION-NEWS'}"> class="selected" </c:if> >News</p></a>
    <a href="themes"><p <c:if test="${requestScope.section == 'SECTION-THEMES'}"> class="selected" </c:if> >Thèmes</p></a>
    <a href="tags"><p <c:if test="${requestScope.section == 'SECTION-TAGS'}"> class="selected" </c:if> >Tags</p></a>
    <c:if test="${requestScope.user == true}"><a href="logout">Logout</a></c:if>
    <a href="login">Login</a>
    <a href="register">Register</a>
</nav>
</header>
