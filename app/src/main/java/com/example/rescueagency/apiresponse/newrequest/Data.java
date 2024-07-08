package com.example.rescueagency.apiresponse.newrequest;

import java.util.List;

public class Data {
    private int request_id;
    private String describe_incident;
    private List<String> incident_images;
    private String type_of_incident;
    private String status;
    private String date;
    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public int getRequest_id() {
        return request_id;
    }

    public String getDescribe_incident() {
        return describe_incident;
    }

    public List<String> getIncident_images() {
        return incident_images;
    }

    public String getType_of_incident() {
        return type_of_incident;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    private String time;
}
