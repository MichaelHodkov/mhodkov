package ru.job4j.crudservlet;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStore {
    private final static UserSQL userSQL = new UserSQL();
    private final static UserStore INSTANCE = new UserStore();

    private UserStore() {
    }

    public static synchronized UserStore getInstance() {
        return INSTANCE;
    }

    public void addUser(User user) {
        userSQL.addUser(user);
    }

    public void updateUser(String id, User userNew) {
        userSQL.updateUser(id, userNew);
    }

    public void delUser(User user) {
        userSQL.delUser(user);
    }

    public void delUser(String id) {
        userSQL.delUser(id);
    }

    public List<User> getUsers() {
        return userSQL.getUsers();
    }
}
