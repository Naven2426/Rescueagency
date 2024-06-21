package com.example.rescueagency.check_status_fragment;

public class CheckStatusList {

    String status;
    String status_name;
    String alert;
    String alert_type;
    String date;
    String date_sent;
    String image;
    String track_agency;

    public CheckStatusList(String status, String status_name, String alert, String alert_type, String date, String date_sent,  String track_agency) {
        this.status = status;
        this.status_name = status_name;
        this.alert = alert;
        this.alert_type = alert_type;
        this.date = date;
        this.date_sent = date_sent;
        this.track_agency = track_agency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert_type() {
        return alert_type;
    }

    public void setAlert_type(String alert_type) {
        this.alert_type = alert_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTrack_agency() {
        return track_agency;
    }

    public void setTrack_agency(String track_agency) {
        this.track_agency = track_agency;
    }
}
