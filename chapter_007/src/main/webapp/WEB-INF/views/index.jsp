<%@ page import="ru.job4j.crudservlet.User" %>
<%@ page import="ru.job4j.servlet.UserStore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
    <br>
    <form action="<%=request.getContextPath()%>/add.jsp" method="post">
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
        <%for (User user : UserStore.getInstance().getUsers()) {%>
        <tr>
            <td><%=user.getId()%></td>
            <td><%=user.getName()%></td>
            <td><%=user.getLogin()%></td>
            <td><%=user.getEmail()%></td>
            <td><%=user.getCreatedate()%></td>
            <td>
                <form action="<%=request.getContextPath()%>/edit.jsp" method="post">
                    <input type="hidden" name="id" value="<%=user.getId()%>">
                    <input type="hidden" name="name" value="<%=user.getName()%>">
                    <input type="hidden" name="login" value="<%=user.getLogin()%>">
                    <input type="hidden" name="email" value="<%=user.getEmail()%>">
                    <input type="submit" value="edit">
                </form>
            </td>
            <td>
                <form action="<%=request.getContextPath()%>/delJSP" method="post">
                    <input type="hidden" name="del_id" value="<%=user.getId()%>">
                    <input type="submit" value="delete">
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <br>
    <form action="<%=request.getContextPath()%>/add.jsp" method="post">
        <input type="submit" value="Add new user">
    </form>
</body>
</html>
