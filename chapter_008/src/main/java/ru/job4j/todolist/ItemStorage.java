package ru.job4j.todolist;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.job4j.models.Item;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ItemStorage {

    private final static ItemStorage INSTANCE = new ItemStorage();
    private SessionFactory factory;
    private Session session;

    private ItemStorage() {
    }

    public static synchronized ItemStorage getInstance() {
        return INSTANCE;
    }

    public void start() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    private void connect() {
        session = factory.openSession();
        session.beginTransaction();
    }

    private void disconnect() {
        session.getTransaction().commit();
        session.close();
    }

    public void end() {
        factory.close();
    }

    public List<Item> getList() {
        connect();
        List<Item> list = new ArrayList<>();
        list = session.createQuery("FROM Item ORDER BY id ASC").list();
        disconnect();
        return list;
    }

    public void addItem(String desc) {
        connect();
        Item item = new Item();
        item.setDesc(desc);
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        item.setDone(false);
        session.saveOrUpdate(item);
        disconnect();
    }

    public void delItem(String id) {
        connect();
        Item delItem = new Item();
        delItem.setId(Integer.parseInt(id));
        session.delete(delItem);
        disconnect();
    }

    public void editItem(String id) {
        connect();
        List<Item> list = session.createQuery(String.format("FROM Item WHERE id = '%s'", id)).list();
        if (!list.isEmpty()) {
            Item item = list.get(0);
            boolean done = item.isDone() ? false : true;
            item.setDone(done);
            session.update(item);
        }
        disconnect();
    }
}
