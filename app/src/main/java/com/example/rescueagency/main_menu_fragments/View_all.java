package com.example.rescueagency.main_menu_fragments;

public class View_all {
    String text;
    String image;
    String id;

    public String getId() {
        return id;
    }

    public View_all(String text, String image, String id) {
        this.text = text;
        this.image = image;
        this.id=id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
