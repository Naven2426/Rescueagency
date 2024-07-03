package com.example.rescueagency.apiresponse.agencymemberinfo;

public class MemberDetails {

    private int status;
    private String message;

    private String agent_id;
    private String name;
    private String mobile;
    private String email;
    private String address;
    private String dob;
    private String role;
    private String year_of_experience;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setYear_of_experience(String year_of_experience) {
        this.year_of_experience = year_of_experience;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private String file;


}
