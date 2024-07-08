package com.example.rescueagency.apiresponse.getnewemergencyrequestinfo;

public class Result {
    private IncidentInformation incident_information;
    private Agent agent;
    private User user;

    public IncidentInformation getIncident_information() {
        return incident_information;
    }

    public Agent getAgent() {
        return agent;
    }

    public User getUser() {
        return user;
    }
}
