package com.example.rescueagency.agency.AgencyReqHistoryList;

public class AgencyReqHistoryList {

    String status;
    String requestId;
    String alert;
    String date;
     String image;

    public String getRequestId() {
        return requestId;
    }

    public AgencyReqHistoryList(String status, String requestId, String alert, String date) {
        this.status = status;
        this.requestId = requestId;
        this.alert = alert;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public String getAlert() {
        return alert;
    }

    public String getDate() {
        return date;
    }

    public  String getImage() {
        return image;
    }

}