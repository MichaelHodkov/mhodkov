package ru.job4j.models;

import java.sql.Timestamp;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Advert {
    private int id;
    private User user;
    private int idBrand;
    private int idModel;
    private String name;
    private String description;
    private Timestamp time;
    private boolean status;
    private byte[] picture;

    public Advert() {
    }

    public Advert(User user, int idBrand, int idModel, String name, String description, byte[] picture) {
        this.user = user;
        this.idBrand = idBrand;
        this.idModel = idModel;
        this.name = name;
        this.description = description;
        this.time = new Timestamp(System.currentTimeMillis());
        this.status = true;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }

    public int getIdModel() {
        return idModel;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public byte[] getPicture() {
        return picture;
    }

    public String getPic() {
        return new String(getPicture());
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
