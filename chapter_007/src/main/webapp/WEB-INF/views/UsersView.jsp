<%@ page import="ru.job4j.crudservlet.User" %>
<%@ page import="ru.job4j.servlet.UserStore" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
    <br>
    <form action="${pageContext.servletContext.contextPath}/add" method="post">
        <input type="submit" value="Add new user">
    </form>
    <br>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Login</th>
            <th>E-mail</th>
            <th>Time create</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.login}</td>
            <td>${user.email}</td>
            <td>${user.createdate}</td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/edit" method="post">
                    <input type="hidden" name="id" value="${user.id}">
                    <input type="hidden" name="name" value="${user.name}">
                    <input type="hidden" name="login" value="${user.login}">
                    <input type="hidden" name="email" value="${user.email}">
                    <input type="submit" value="edit">
                </form>
            </td>
            <td>
                <form action="${pageContext.servletContext.contextPath}/delete" method="post">
                    <input type="hidden" name="del_id" value="${user.id}">
                    <input type="submit" value="delete">
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
    <br>
    <form action="${pageContext.servletContext.contextPath}/add" method="post">
        <input type="submit" value="Add new user">
    </form>
</body>
</html>
