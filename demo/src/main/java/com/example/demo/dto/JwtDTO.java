package com.example.demo.dto;

import com.example.demo.model.User;

public class JwtDTO extends ResponseDTO {
    private String token;
    private long expiresIn;
    private char userType;

    public JwtDTO(String message, int code, String token, long expiresIn, char userType) {
        super(message, code);
        this.token = token;
        this.expiresIn = expiresIn;
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
