package com.example.rescueagency.agency;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class NewRequestData {
    RequestBody agent_id,agency_name;

    public void setAgent_id(String agent_id) {
        this.agent_id = RequestBody.create(MediaType.parse("text/plain"),agent_id);
    }
}
