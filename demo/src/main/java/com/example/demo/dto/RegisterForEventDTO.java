package com.example.demo.dto;

public class RegisterForEventDTO {
    private String message;
    private String eventId;
    private String userId;

    public RegisterForEventDTO(String message, String eventId, String userid) {
        this.message = message;
        this.eventId = eventId;
        this.userId = userid;
    }

    public RegisterForEventDTO(String eventId) {
        this.eventId = eventId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }    
}
