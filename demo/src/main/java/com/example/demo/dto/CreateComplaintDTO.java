package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.example.demo.model.ComplaintMedia;

public class CreateComplaintDTO {
    private String title;
    private String description;
    private LocalDateTime dateTime;
    // private Set<ComplaintMedia> photos;
    private String status;

    public CreateComplaintDTO(String title, String description, String status) {
        this.title = title;
        this.description = description;
        dateTime = LocalDateTime.now();
        // this.photos = photos;
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
