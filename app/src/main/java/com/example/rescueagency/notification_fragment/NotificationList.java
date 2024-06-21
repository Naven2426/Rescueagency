package com.example.rescueagency.notification_fragment;

public class NotificationList {

    String status;
    String status_name;
    String alert;
    String alert_type;
    String date;
    String date_sent;
    String feedback;
    String send;

    public NotificationList(String status, String status_name, String alert, String alert_type, String date, String date_sent, String feedback, String send) {
        this.status = status;
        this.status_name = status_name;
        this.alert = alert;
        this.alert_type = alert_type;
        this.date = date;
        this.date_sent = date_sent;
        this.feedback = feedback;
        this.send = send;
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }


}
