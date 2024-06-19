package com.example.rescueagency.admin.HomeFragment;

public class AdminAgencyViewList {


    private String agencyname;
    private String rating;
    private String address;
    private  String agencyimage;

    private String agencytype;

    public String getAgencyname() {
        return agencyname;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgencyimage() { return agencyimage;}

    public void setAgencyimage(String agencyimage) {this.agencyimage = agencyimage;}

    public  String getAgencytype() {return agencytype;   }
    public void setAgencytype(String agencytype) {
        this.agencytype = agencytype;
    }




    public AdminAgencyViewList(String agencyname, String rating, String address, String agencyimage, String agencytype) {

        this.agencyname = agencyname;
        this.rating = rating;
        this.address = address;
        this.agencyimage = agencyimage;
        this.agencytype = agencytype;

    }



}
