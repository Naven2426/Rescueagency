package com.example.rescueagency.admin.HomeFragment;

public class AdminUserViewList {

    private String profileimage;
    private String username;
    private String name;

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdminUserViewList(String profileimage,String username,String name){

        this.profileimage = profileimage;
        this.username     = username;
        this.name         = name;
    }
}
