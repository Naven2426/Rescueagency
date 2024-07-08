package com.example.rescueagency.apiresponse.map.getcurrentlocation;

import java.util.List;

public class CurrentLocationRootClass {
    private List<Results> results;
    private String status;
    private String error_message;

    public String getError_message() {
        return error_message;
    }

    public List<Results> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}
