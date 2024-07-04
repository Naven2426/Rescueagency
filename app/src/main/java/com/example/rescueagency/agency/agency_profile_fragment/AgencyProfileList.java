package com.example.rescueagency.agency.agency_profile_fragment;

public class AgencyProfileList {

    private String text;
    private String image;
    private String memberID;
    private String email;
    private String phone;
    private String address;
    private String dob;
    private String role;
    private String yearofexperience;;

    public AgencyProfileList(String text, String image, String memberID, String email, String phone, String address, String dob, String role, String yearofexperience) {
        this.text = text;
        this.image = image;
        this.memberID = memberID;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.role = role;
        this.yearofexperience = yearofexperience;
    }

    public AgencyProfileList(String text, String image) {
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

    public String getMemberID() {
        return memberID;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDob() {
        return dob;
    }

    public String getRole() {
        return role;
    }

    public String getYearofexperience() {
        return yearofexperience;
    }
}
