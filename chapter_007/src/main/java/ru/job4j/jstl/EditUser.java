package ru.job4j.jstl;

import ru.job4j.crudservlet.User;
import ru.job4j.servlet.UserStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class EditUser extends HttpServlet {
    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        if (name == null || name.equals("") || login == null || login.equals("") || email == null || email.equals("")) {
            req.setAttribute("name", name);
            req.setAttribute("login", login);
            req.setAttribute("email", email);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
        } else {
            users.updateUser(id, new User(name, login, email, new Date()));
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        req.setAttribute("id", id);
        req.setAttribute("name", name);
        req.setAttribute("login", login);
        req.setAttribute("email", email);
        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }
}
