package ru.job4j.crudservlet;

import java.util.Date;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class User {
    private String id;
    private String name;
    private String login;
    private String email;
    private Date createdate;

    public User() {
    }

    public User(String name, String login, String email, Date createdate) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createdate = createdate;
    }

    public User(String id, String name, String login, String email, Date createdate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.createdate = createdate;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Override
    public String toString() {
        return String.format("name: %s, log: %s, e-mail: %s, create date: %s", name, login, email, createdate);
    }
}
