package com.example.rescueagency.apiresponse;

import java.util.List;

public class GetAgencies {
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

    public static  class Data{
        private int id;
        private String name;
        private long phone;
        private String email;
        private String address;
        private String user_type;
        private int total_members;
        private String profile;
        private double latitude;
        private double longitude;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public long getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }

        public String getUser_type() {
            return user_type;
        }

        public int getTotal_members() {
            return total_members;
        }

        public String getProfile() {
            return profile;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
