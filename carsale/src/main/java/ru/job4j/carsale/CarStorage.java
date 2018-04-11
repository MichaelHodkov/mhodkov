package ru.job4j.carsale;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
import ru.job4j.models.Model;
import ru.job4j.models.User;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public enum CarStorage {

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
        session.clear();
    }

    public void finish() {
        session.close();
        factory.close();
    }

    public List getList(String table) {
        connect();
        List list = session.createQuery(String.format("FROM %s ORDER BY id ASC", table)).list();
        disconnect();
        return list;
    }

    public List<Advert> getActivAdvert() {
        connect();
        List<Advert> list = session.createQuery("FROM Advert WHERE status = true ORDER BY id ASC").list();
        disconnect();
        return list;
    }

    public List<Advert> getAdvertDay() {
        connect();
        Query query = session.createQuery("FROM Advert WHERE timecreated > :time ORDER BY id ASC");
        query.setParameter("time", new Timestamp(System.currentTimeMillis() - 86400000));
        List<Advert> list = query.list();
        disconnect();
        return list;
    }

    public List<Advert> getAdvertBrand(int idBrand) {
        connect();
        Query query = session.createQuery("FROM Advert WHERE id_brand = :idBrand ORDER BY id ASC");
        query.setParameter("idBrand", idBrand);
        List<Advert> list = query.list();
        disconnect();
        return list;
    }

    public void addObject(Object obj) {
        connect();
        session.saveOrUpdate(obj);
        disconnect();
    }

    public void delObject(Object obj) {
        connect();
        session.delete(obj);
        disconnect();
    }

    public Brand getBrand(String brandName) {
        connect();
        Query query = session.createQuery("FROM Brand WHERE name = :brand");
        query.setParameter("brand", brandName);
        List list = query.list();
        disconnect();
        Brand brand = null;
        if (list.size() > 0) {
            brand = (Brand) query.list().get(0);
        }
        return brand;
    }

    public User getUser(int id) {
        connect();
        User user = session.get(User.class, id);
        disconnect();
        return user;
    }

    public Brand getBrand(int id) {
        connect();
        Brand brand = session.get(Brand.class, id);
        disconnect();
        return brand;
    }

    public Model getModel(int id) {
        connect();
        Model model = session.get(Model.class, id);
        disconnect();
        return model;
    }

    public Advert getAdvert(int id) {
        connect();
        Advert advert = session.get(Advert.class, id);
        disconnect();
        return advert;
    }

    public List<Model> getModels(int idBrand) {
        connect();
        Query query = session.createQuery("FROM Model WHERE id_brand = :idBrand");
        query.setParameter("idBrand", idBrand);
        List list = query.list();
        disconnect();
        return list;
    }

    public List<Advert> getAdvertUser(int idUser) {
        connect();
        Query query = session.createQuery("FROM Advert WHERE id_user = :idUser");
        query.setParameter("idUser", idUser);
        List list = query.list();
        disconnect();
        return list;
    }
}
