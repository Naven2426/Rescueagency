package com.example.rescueagency.agency.AgencyReqHistoryList;

public class AgencyReqHistoryList {

    String status;
    String statusID;
    String alert;
    String alertID;
    String date;
    String dateID;

    static String image;

    public AgencyReqHistoryList(String status, String statusID, String alert, String alertID, String date, String dateID, String image) {
        this.status = status;
        this.statusID = statusID;
        this.alert = alert;
        this.alertID = alertID;
        this.date = date;
        this.dateID = dateID;
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlertID() {
        return alertID;
    }

    public void setAlertID(String alertID) {
        this.alertID = alertID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateID() {
        return dateID;
    }

    public void setDateID(String dateID) {
        this.dateID = dateID;
    }

    public static String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
