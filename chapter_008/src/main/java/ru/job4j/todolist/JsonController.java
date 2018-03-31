package ru.job4j.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.models.Item;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class JsonController extends HttpServlet {
    private final ItemStorage items = ItemStorage.getInstance();

    @Override
    public void init() throws ServletException {
        items.start();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        List<Item> list = items.getList();
        mapper.writeValue(writer, items.getList());
        writer.flush();
    }

    @Override
    public void destroy() {
        items.end();
    }
}
