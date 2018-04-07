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
public class ServletMainPage extends HttpServlet {
    private final CarStorage carStorage = CarStorage.getINSTANCE();

    @Override
    public void init() throws ServletException {
        carStorage.start();
        new DefaultValue().addBrand();
        new DefaultValue().addModel();
        new DefaultValue().addRoot();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/MainPage.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        carStorage.finish();
    }
}
