<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Autoresation</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/SignIn" method="post">
    <table>
        <tr><td>Login:</td><td><input type="text" name="login" value="${requestScope.get("login")}"></td></tr>
        <tr><td>E-mail:</td><td><input type="text" name="email" value="${requestScope.get("email")}"></td></tr>
        <tr><td></td><td> <input type="submit" value="Join"></td></tr>
    </table>
</form>
<br>
<c:if test="${error ne ''}">
    ${requestScope.get("error")}
</c:if>
</body>
</html>
