package com.example.demo.dto;

public class ResponseDTO {
    private String message;
    private int code;

    public ResponseDTO(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ResponseDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
