package com.example.rescueagency.apiresponse.newrequest;

import java.util.List;

public class NewRequestRootClass {
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
