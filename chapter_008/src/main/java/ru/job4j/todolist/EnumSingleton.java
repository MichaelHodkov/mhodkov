package ru.job4j.todolist;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.Item;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public enum EnumSingleton {
    INSTANCE;

    private SessionFactory factory;
    private Session session;

    public void start() {
        factory = new Configuration().configure().buildSessionFactory();
        session = factory.openSession();
    }

    private void connect() {
        session.beginTransaction();
    }

    private void disconnect() {
        session.getTransaction().commit();
    }

    public void finish() {
        session.close();
        factory.close();
    }

    public List<Item> getList() {
        connect();
        List<Item> list = session.createQuery("FROM Item ORDER BY id ASC").list();
        disconnect();
        return list;
    }

    public void addOrUpadateItem(Item item) {
        connect();
        session.saveOrUpdate(item);
        disconnect();
    }

    public Item getItem(String id) {
        connect();
        List<Item> list = session.createQuery(String.format("FROM Item WHERE id = '%s'", id)).list();
        Item item = null;
        if (!list.isEmpty()) {
            item = list.get(0);
        }
        disconnect();
        return item;
    }
}
