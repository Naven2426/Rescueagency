package com.example.rescueagency.admin.AdminAgencyViewDetailList;

public class AdminAgencyViewDetailList {
    String text;
    String image;

    public AdminAgencyViewDetailList(String text, String image) {
        this.text = text;
        this.image = image;
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
