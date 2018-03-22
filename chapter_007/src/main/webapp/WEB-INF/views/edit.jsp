<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
    <form action="${pageContext.servletContext.contextPath}/edit" method="get">
        <table>
            <td> Name: <input type="text" name="name" value="${requestScope.get("name")}"></td>
            <td> Login: <input type="text" name="login" value="${requestScope.get("login")}"></td>
            <td> E-mail: <input type="text" name="email" value="${requestScope.get("email")}"></td>
            <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
            <td> <input type="submit" value="Edit user"></td>
        </table>
    </form>
    <br>
    <form action="${pageContext.servletContext.contextPath}/" method="get">
        <input type="submit" value="Back">
    </form>
</body>
</html>
