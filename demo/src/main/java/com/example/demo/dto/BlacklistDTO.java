package com.example.demo.dto;


public class BlacklistDTO {
    public String userId;

    public BlacklistDTO(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}