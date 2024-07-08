package com.example.rescueagency.agency.SOSRequestRVFragment;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class AgencySOSReqList {

    String name;
    String requestId;
    String date_sent;
    String latitude;
    String alertType;
    String longitude;
    private String requestType;


    public AgencySOSReqList(String name, String requestId, String date_sent, String alertType) {
        this.name = name;
        this.requestId = requestId;
        this.date_sent = date_sent;
        this.alertType=alertType;
    }

    public String getName() {
        return name;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAlertType() {
        return alertType;
    }

    public String getLongitude() {
        return longitude;
    }


    public String getRequestType() {
        return requestType;
    }
}
