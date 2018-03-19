<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
    <form action="<%=request.getContextPath()%>/editJSP" method="post">
        <table>
            <td> Name: <input type="text" name="name" value="<%=request.getParameter("name") == null ? "" : request.getParameter("name")%>"></td>
            <td> Login: <input type="text" name="login" value="<%=request.getParameter("login") == null ? "" : request.getParameter("login")%>"></td>
            <td> E-mail: <input type="text" name="email" value="<%=request.getParameter("email") == null ? "" : request.getParameter("email")%>"></td>
            <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
            <td> <input type="submit" value="Edit user"></td>
        </table>
    </form>
    <br>
    <form action="index.jsp" method="get">
        <input type="submit" value="Back">
    </form>
</body>
</html>
