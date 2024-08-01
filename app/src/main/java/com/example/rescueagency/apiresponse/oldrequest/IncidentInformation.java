package com.example.rescueagency.apiresponse.oldrequest;

import java.util.List;

public class IncidentInformation {
    private int requestId;
    private String roomId;
    private List<String> incidentImages;
    private String describeIncident;
    private String status;
    private long latitude;
    private int longitude;
    private String time;
    private String date;

    public int getRequestId() {
        return requestId;
    }

    public String getRoomId() {
        return roomId;
    }

    public List<String> getIncidentImages() {
        return incidentImages;
    }

    public String getDescribeIncident() {
        return describeIncident;
    }

    public String getStatus() {
        return status;
    }

    public long getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
