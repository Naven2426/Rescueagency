package com.example.rescueagency.agency;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NewRequestData {
    RequestBody agent_id,agency_name,user_id,user_name,mobile,describe_incident,category_id,type_of_incident,status,latitudue,longitude;

    public void setAgent_id(String agent_id) {
        this.agent_id = RequestBody.create(MediaType.parse("text/plain"),agent_id);
    }
    public void setAgency_name(String agency_name) {
        this.agency_name = RequestBody.create(MediaType.parse("text/plain"),agency_name);
    }
    public void setUser_id(String user_id) {
        this.user_id = RequestBody.create(MediaType.parse("text/plain"),user_id);
    }
    public void setUser_name(String user_name) {
        this.user_name = RequestBody.create(MediaType.parse("text/plain"),user_name);
    }
    public void setMobile(String mobile) {
        this.mobile = RequestBody.create(MediaType.parse("text/plain"),mobile);
    }
    public void setDescribe_incident(String describe_incident) {
        this.describe_incident = RequestBody.create(MediaType.parse("text/plain"),describe_incident);
    }
    public void setCategory_id(String category_id) {
        this.category_id = RequestBody.create(MediaType.parse("text/plain"),category_id);
    }
    public void setType_of_incident(String type_of_incident) {
        this.type_of_incident = RequestBody.create(MediaType.parse("text/plain"),type_of_incident);
    }
    public void setStatus(String status) {
        this.status = RequestBody.create(MediaType.parse("text/plain"),status);
    }
    public void setLatitudue(String latitudue) {
        this.latitudue = RequestBody.create(MediaType.parse("text/plain"),latitudue);
    }
    public void setLongitude(String longitude) {
        this.longitude = RequestBody.create(MediaType.parse("text/plain"),longitude);
    }

}
