package ru.job4j.carsale;

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
public class ServletViewActiveAdvertPage extends HttpServlet {
    private final CarStorage carStorage = CarStorage.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("::test::" + carStorage.getAdvert(Integer.parseInt(req.getParameter("id"))).getPicture().length);
        req.getRequestDispatcher("/WEB-INF/views/ViewActiveAdvertPage.jsp").forward(req, resp);
    }
}
