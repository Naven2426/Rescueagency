package com.example.rescueagency.apiresponse.agencyinfo;

import java.util.List;

public class AgencyInfoRoot {
    private int status;
    private String message;
    private Data data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }
}
