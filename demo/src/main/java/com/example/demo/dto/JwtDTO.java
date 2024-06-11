package com.example.demo.dto;

import com.example.demo.model.User;

public class JwtDTO {
    private String token; // take as email id first

    public JwtDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
