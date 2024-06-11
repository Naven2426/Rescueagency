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
