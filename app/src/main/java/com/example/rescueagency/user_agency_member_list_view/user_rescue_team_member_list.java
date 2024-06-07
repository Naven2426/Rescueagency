package com.example.rescueagency.user_agency_member_list_view;

public class user_rescue_team_member_list {

    String name;
    String experience;
    String position;
    String image;

    public user_rescue_team_member_list(String experience, String image, String name, String position) {

        this.name = name;
        this.experience = experience;
        this.image = image;
        this.position = position;

    }

    public  String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getText() {
        return experience;
    }

    public void setText(String text) {
        this.experience = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public  String getPosition() { return position;}

    public void setPosition(String position) { this.position = position;}


}
