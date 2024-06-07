package com.example.demo.dto;

import com.example.demo.model.User;

public class UserResponse extends MessageResponse {
    private User user;
    private char userType;

    public UserResponse(String message, int code, User user, char userType) {
        super(message, code);
        this.user = user;
        this.userType = userType;
    }

    public UserResponse() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public char getUserType() {
        return userType;
    }

    public void setUserType(char userType) {
        this.userType = userType;
    }
}
