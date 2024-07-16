package com.example.rescueagency.check_status_fragment;

public class CheckStatusList {

    private final String roomId;
    private final String status_name;
    private final String alert_type;
    private final String date;
    private final String from;

    public CheckStatusList(String roomId, String status_name, String alert_type, String date, String from) {
        this.roomId = roomId;
        this.status_name = status_name;
        this.alert_type = alert_type;
        this.date = date;
        this.from = from;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getFrom() {
        return from;
    }

    public String getStatus_name() {
        return status_name;
    }

    public String getAlert_type() {
        return alert_type;
    }

    public String getDate() {
        return date;
    }

}
