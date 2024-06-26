package com.example.rescueagency.agency.SOSRequestRVFragment;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class AgencySOSReqList {

    String name;
    String name_id;
    String date;
    String date_sent;
    String location;
    String alertType;
    String location_id;
    String image;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_id() {
        return name_id;
    }

    public void setName_id(String name_id) {
        this.name_id = name_id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public AgencySOSReqList(String name, String name_id, String date, String date_sent, String location, String location_id, String image,String status,
                            String alertType) {
        this.name = name;
        this.name_id = name_id;
        this.date = date;
        this.date_sent = date_sent;
        this.location = location;
        this.location_id = location_id;
        this.image = image;
        this.status=status;
        this.alertType=alertType;
    }

    public String getAlertType() {
        return alertType;
    }

    public String getStatus() {
        return status;
    }
}
