package ru.job4j.todolist;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class AddServlet extends HttpServlet {
    private final ItemStorage items = ItemStorage.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        items.addItem(req.getParameter("desc"));
    }
}
