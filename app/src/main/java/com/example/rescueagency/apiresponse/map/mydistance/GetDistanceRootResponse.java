package com.example.rescueagency.apiresponse.map.mydistance;

import java.util.List;

public class GetDistanceRootResponse {
    private List<String> destinationAddresses;
    private List<String> originAddresses;
    private List<Rows> rows;
    private String error_message;

    public String getError_message() {
        return error_message;
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public String getStatus() {
        return status;
    }

    private String status;
}
