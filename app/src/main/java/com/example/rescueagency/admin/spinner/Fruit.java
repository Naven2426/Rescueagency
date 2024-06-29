package com.example.rescueagency.admin.spinner;
import java.io.Serializable;

public class Fruit implements Serializable {

    private String name;
    private String image;

    public Fruit() {
    }
    private String id;

    public String getId() {
        return id;
    }

    public Fruit(String name, String image, String id) {
        this.name = name;
        this.image = image;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}