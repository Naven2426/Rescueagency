package com.example.rescueagency.apiresponse.checkstatus;

import java.util.List;

public class CheckStatusResponseRootClass {
    private int status;
    private String message;
    private List<Data> data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Data> getData() {
        return data;
    }
}
