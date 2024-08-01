package com.example.rescueagency.apiresponse;

public class SendFeedBackDataClass {
    private String agentId;
    private String userId;
    private String requestId;
    private String typeOfIncident;
    private String command;
    private String rating;

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setTypeOfIncident(String typeOfIncident) {
        this.typeOfIncident = typeOfIncident;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
