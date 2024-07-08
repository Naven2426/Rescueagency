package com.example.rescueagency.apiresponse.getnewemergencyrequestinfo;

import java.util.List;

public class IncidentInformation {
    private int request_id;
    private String room_id;
    private String type_of_incident;
    private List<String> incident_images;
    private String describe_incident;
    private String status;
    private double latitude;
    private double longitude;

    public int getRequest_id() {
        return request_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public String getType_of_incident() {
        return type_of_incident;
    }

    public List<String> getIncident_images() {
        return incident_images;
    }

    public String getDescribe_incident() {
        return describe_incident;
    }

    public String getStatus() {
        return status;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    private String time;
    private String date;
}
