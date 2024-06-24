package com.example.rescueagency.agency.agency_review_list;

public class AgencyReviewList {
    private  String agencyName, agencyReview,username,rating,profile;

    public AgencyReviewList(String agencyName, String agencyReview, String username, String rating, String profile) {
        this.agencyName = agencyName;
        this.agencyReview = agencyReview;
        this.username = username;
        this.rating = rating;
        this.profile = profile;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyReview() {
        return agencyReview;
    }

    public void setAgencyReview(String agencyReview) {
        this.agencyReview = agencyReview;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }


}
