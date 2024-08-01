package com.example.rescueagency.apiresponse.oldrequest;

public class OldRequestRootClass {
    private int status;
    private String message;
    private IncidentInformation incidentInformation;
    private Agent agent;
    private User user;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public IncidentInformation getIncidentInformation() {
        return incidentInformation;
    }

    public Agent getAgent() {
        return agent;
    }

    public User getUser() {
        return user;
    }
}
