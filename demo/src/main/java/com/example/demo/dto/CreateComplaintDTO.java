package com.example.demo.dto;

import java.time.LocalDateTime;

public class CreateComplaintDTO {
    private String title;
    private String description;
    private String complainee;
    private LocalDateTime dateTime;
    private String status;

    public CreateComplaintDTO(String title, String description, String complainee, String status) {
        this.title = title;
        this.description = description;
        this.complainee = complainee;
        dateTime = LocalDateTime.now();
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComplainee() {
        return complainee;
    }

    public void setComplainee() {
        this.complainee = complainee;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    
}
