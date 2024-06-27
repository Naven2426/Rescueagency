package com.example.rescueagency.apiresponse.map.ditance;

public class Elements {
    private Distance distance;
    private Duration duration;
    private Duration_in_traffic duration_in_traffic;
    private String status;

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
    public Distance getDistance() {
        return distance;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public Duration getDuration() {
        return duration;
    }
    public void setDuration_in_traffic(Duration_in_traffic duration_in_traffic) {
        this.duration_in_traffic = duration_in_traffic;
    }
    public Duration_in_traffic getDuration_in_traffic() {
        return duration_in_traffic;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
