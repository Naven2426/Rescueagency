package com.example.rescueagency.apiresponse.map.ditance;

import java.util.List;

public class DitanceAndDurationRoot {
    private List<String> destinationAddresses;
    private List<String> originAddresses;
    private List<Rows> rows;
    private String status;

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }
    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }
    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }
    public List<String> getOriginAddresses() {
        return originAddresses;
    }
    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }
    public List<Rows> getRows() {
        return rows;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
