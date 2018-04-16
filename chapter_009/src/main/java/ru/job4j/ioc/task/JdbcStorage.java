package ru.job4j.ioc.task;

import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class JdbcStorage implements Storage<User> {
    private static final Logger LOG = Logger.getLogger(JdbcStorage.class);
    private Connection conn = null;
    private final String url;
    private final String username;
    private final String password;
    private final boolean autoCommit;

    public JdbcStorage(String url, String username, String password, boolean autoCommit) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.autoCommit = autoCommit;
        execute("CREATE TABLE IF NOT EXISTS users (id serial PRIMARY KEY, name CHARACTER VARYING(100) NOT NULL);");
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            LOG.error(String.format("Error: open connect (%s)", e));
        }
    }

    private void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.error(String.format("Error: close connect (%s)", e));
            }
        }
    }

    private void execute(String sql) {
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
            close();
        } catch (SQLException e) {
            LOG.error(String.format("Error: execute %s (%s)", sql, e));
        }
    }

    private List<User> executeGet(String sql) {
        try {
            connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                list.add(new User(id, name));
            }
            ps.close();
            close();
            return list;
        } catch (SQLException e) {
            LOG.error(String.format("Error: executeGet %s (%s)", sql, e));
        }
        return null;
    }

    @Override
    public boolean add(User entity) {
        if (!find(entity)) {
            execute(String.format("INSERT INTO users (name) VALUES ('%s');", entity.getName()));
            return true;
        }
        return false;
    }

    @Override
    public boolean del(User entity) {
        if (find(entity)) {
            execute(String.format("DELETE FROM users WHERE id = %d;", entity.getId()));
            return true;
        }
        return false;
    }

    @Override
    public boolean del(int id) {
        if (find(id) != null) {
            execute(String.format("DELETE FROM users WHERE id = %d;", id));
            return true;
        }
        return false;
    }

    @Override
    public boolean find(User entity) {
        List<User> list = executeGet("SELECT * FROM users");
        for (User user: list) {
            if (user.equals(entity)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User find(String name) {
        List<User> list = executeGet(String.format("SELECT * FROM users WHERE name = '%s'", name));
        for (User user: list) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User find(int id) {
        List<User> list = executeGet(String.format("SELECT * FROM users WHERE id = %d", id));
        for (User user: list) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return executeGet("SELECT * FROM users;");
    }
}
