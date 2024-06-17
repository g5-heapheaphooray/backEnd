package com.example.demo.dto;

public class RegisterForEventDTO extends ResponseDTO {
    private String eventId;
    private String userId;

    public RegisterForEventDTO(String message, int code, String eventId, String userid) {
        super(message, code);
        this.eventId = eventId;
        this.userId = userid;
    }

    public RegisterForEventDTO(String eventId) {
        this.eventId = eventId;
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
