package com.example.demo.dto;

import com.example.demo.model.User;

public class JwtDTO extends ResponseDTO {
    private String token;
    private long expiresIn;

    public JwtDTO(String message, int code, String token, long expiresIn) {
        super(message, code);
        this.token = token;
        this.expiresIn = expiresIn;
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
