package com.example.rescueagency.apiresponse.checkstatus;

import java.util.List;

public class IncidentInformation {
    private int request_id;
    private String room_id;
    private List<String> incident_images;
    private String describe_incident;
    private String status;
    private double latitude;
    private double longitude;
    private String time;
    private String date;

    public int getRequest_id() {
        return request_id;
    }

    public String getRoom_id() {
        return room_id;
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
}
