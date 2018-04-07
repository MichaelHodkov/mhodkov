<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.job4j.carsale.CarStorage" %>
<%@ page import="ru.job4j.models.Advert" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Cars</title>
</head>
<body>
<div>
    <% if (session.getAttribute("login") == null || ((String) session.getAttribute("login")).isEmpty()) { %>
    <h4><a href="${pageContext.servletContext.contextPath}/login">Войти</a></h4>
        <% } else { %>
    <h4>Привет, <%=session.getAttribute("login")%></h4>
    <% if (CarStorage.getINSTANCE().getAdvertUser((Integer) session.getAttribute("user_id")).size() > 0) { %>
    <h5><a href="${pageContext.servletContext.contextPath}/useradvert">Мои объявления</a></h5>
    <% } %>
    <h5><a href="${pageContext.servletContext.contextPath}/exit">Выйти</a></h5>
    <% } %>
</div>
<div>
    <br>
    <form action="${pageContext.servletContext.contextPath}/add" method="get">
        <input type="submit" value="Добавить объявление">
    </form>

</div>
<div>
    <table border="1">
        <% List<Advert> list = CarStorage.getINSTANCE().getActivAdvert(); %>
        <% if (list.size() > 0) { %>
            <tr>
                <td>Brand</td>
                <td>Model</td>
                <td>Short desc</td>
                <td>Time</td>
                <td>Author</td>
                <td>Link</td>
            </tr>
            <% for (Advert advert : list) { %>
            <tr>
                <td><%=CarStorage.getINSTANCE().getBrand(advert.getIdBrand()).getName()%></td>
                <td><%=CarStorage.getINSTANCE().getModel(advert.getIdModel()).getName()%></td>
                <td><%=advert.getName()%></td>
                <td><%=advert.getTime()%></td>
                <td><%=advert.getUser().getLogin()%></td>
                <td><a href="${pageContext.servletContext.contextPath}/view?id=<%=advert.getId()%>">link</a></td>
            </tr>
        <% }} %>
    </table>
</div>
</body>
</html>
