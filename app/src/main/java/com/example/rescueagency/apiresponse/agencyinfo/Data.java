package com.example.rescueagency.apiresponse.agencyinfo;

import java.util.List;

public class Data {
    private int team_id;
    private String team_name;
    private long team_contact ;
    private String team_email;
    private String team_address;
    private String user_type;
    private String type_of_service;
    private int total_team_members;
    private int category_id;
    private String team_profile;
    private List<Member> member;

    public int getTeam_id() {
        return team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public long getTeam_contact() {
        return team_contact;
    }

    public String getTeam_email() {
        return team_email;
    }

    public String getTeam_address() {
        return team_address;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getType_of_service() {
        return type_of_service;
    }

    public int getTotal_team_members() {
        return total_team_members;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getTeam_profile() {
        return team_profile;
    }

    public List<Member> getMember() {
        return member;
    }
}
