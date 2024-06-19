package com.example.rescueagency.apiresponse;

public class SignUpResponse {
    private int status;
    private String message;

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public class User {
        private String id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private String dob;
        private String username;
        private String password;
        private String user_type;
        private String type_of_service;
        private String total_members;
        private String registration_proof;
        private String profile;

        public String getType_of_service() {
            return type_of_service;
        }

        public void setType_of_service(String type_of_service) {
            this.type_of_service = type_of_service;
        }

        public String getTotal_members() {
            return total_members;
        }

        public void setTotal_members(String total_members) {
            this.total_members = total_members;
        }

        public String getRegistration_proof() {
            return registration_proof;
        }

        public void setRegistration_proof(String registration_proof) {
            this.registration_proof = registration_proof;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getEmail() {
            return email;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getPhone() {
            return phone;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddress() {
            return address;
        }
        public void setDob(String dob) {
            this.dob = dob;
        }
        public String getDob() {
            return dob;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getUsername() {
            return username;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getPassword() {
            return password;
        }
    }

}
