package ru.job4j.servlet;

import ru.job4j.crudservlet.User;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStore {
    private final static PoolSQL POOL_SQL = new PoolSQL();
    private final static UserStore INSTANCE = new UserStore();

    private UserStore() {
    }

    public static synchronized UserStore getInstance() {
        return INSTANCE;
    }

    public void addUser(User user) {
        POOL_SQL.addUser(user);
    }

    public void updateUser(String id, User userNew) {
        POOL_SQL.updateUser(id, userNew);
    }

    public void delUser(User user) {
        POOL_SQL.delUser(user);
    }

    public void delUser(String id) {
        POOL_SQL.delUser(id);
    }

    public List<User> getUsers() {
        return POOL_SQL.getUsers();
    }
}
