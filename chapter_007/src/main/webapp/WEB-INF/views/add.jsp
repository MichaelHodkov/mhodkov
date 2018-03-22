<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add user</title>
</head>
    <body>
        <br>
        <form action="<%=request.getContextPath()%>/addJSP" method="post">
            <table>
                <td> Name: <input type="text" name="name" value="<%=request.getParameter("name") == null ? "" : request.getParameter("name")%>"></td>
                <td> Login: <input type="text" name="login" value="<%=request.getParameter("login") == null ? "" : request.getParameter("login")%>"></td>
                <td> E-mail: <input type="text" name="email" value="<%=request.getParameter("email") == null ? "" : request.getParameter("email")%>"></td>
                <td> <input type="submit" value="Add new user"></td>
            </table>
        </form>
        <br>
        <form action="index.jsp" method="get">
            <input type="submit" value="Back">
        </form>
    </body>
</html>
