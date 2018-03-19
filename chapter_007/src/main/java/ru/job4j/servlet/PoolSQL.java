package ru.job4j.servlet;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import ru.job4j.crudservlet.User;


/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class PoolSQL {
    private final static Logger LOG = Logger.getLogger(PoolSQL.class);
    private Connection connection;
    private PreparedStatement preparedStatement;
    private static final PoolingDataSource POOL = new PoolingDataSource();

    public PoolSQL() {
        DriverManagerConnectionFactory dmcf = new DriverManagerConnectionFactory(
                "jdbc:postgresql://localhost:5432/crud", "postgres", "Qwerty123");
        new PoolableConnectionFactory(dmcf, new GenericObjectPool() {
            {
                POOL.setPool(this);
            }
        },
                null, null, false, true);
    }

    private void connect() {
        try {
            connection = POOL.getConnection();
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    private void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException e) {
                LOG.error(e);
            }
        }
    }


    public void createTable() {
        connect();
        try {
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS crud (id serial PRIMARY KEY, name CHARACTER VARYING(100) NOT NULL, login CHARACTER VARYING(100) NOT NULL, email CHARACTER VARYING(100) NOT NULL, createdate TIMESTAMP NOT NULL);");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            disconnect();
        }
    }

    public void addUser(User user) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO crud (name, login, email, createdate) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setTimestamp(4, new Timestamp(user.getCreatedate().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            disconnect();
        }
    }

    public void updateUser(String id, User user) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("UPDATE crud SET name = ?, login = ? , email = ?, createdate = ? WHERE id = ?;");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setTimestamp(4, new Timestamp(user.getCreatedate().getTime()));
            preparedStatement.setInt(5, Integer.valueOf(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            disconnect();
        }
    }

    public void delUser(User user) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM crud WHERE name = ? and login = ? and email = ? and createdate = ?;");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setTimestamp(4, new Timestamp(user.getCreatedate().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            disconnect();
        }
    }

    public void delUser(String id) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM crud WHERE id = ?;");
            preparedStatement.setInt(1, Integer.valueOf(id));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            disconnect();
        }
    }

    public List<User> getUsers() {
        List<User> list = new LinkedList<>();
        connect();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM crud ORDER BY id;");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setLogin(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setCreatedate(new Date(rs.getTimestamp(5).getTime()));
                list.add(user);
            }
        } catch (SQLException e) {
            LOG.error(e);
        } finally {
            disconnect();
        }
        return list;
    }
}