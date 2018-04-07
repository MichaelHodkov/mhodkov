package ru.job4j.models;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Model {
    private int id;
    private String name;
    private int idBrand;

    public Model() {
    }

    public Model(String name, int idBrand) {
        this.name = name;
        this.idBrand = idBrand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(int idBrand) {
        this.idBrand = idBrand;
    }
}
