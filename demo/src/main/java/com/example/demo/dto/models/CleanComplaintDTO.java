package com.example.demo.dto.models;

import java.time.LocalDateTime;
import java.util.List;

public class CleanComplaintDTO {
    private int id;
    private String userId;
    private LocalDateTime dateTime;
    private String title;
    private String description;
    private String status;
    private List<String> photosFilepaths;
    private List<byte[]> photosBytes;

    public CleanComplaintDTO(int id, String userId, LocalDateTime dateTime, String title, String description, String status, List<String> photosFilepaths) {
        this.id = id;
        this.userId = userId;
        this.dateTime = dateTime;
        this.title = title;
        this.description = description;
        this.status = status;
        this.photosFilepaths = photosFilepaths;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPhotosFilepaths() {
        return photosFilepaths;
    }

    public void setPhotosFilepaths(List<String> photosFilepaths) {
        this.photosFilepaths = photosFilepaths;
    }

    public List<byte[]> getPhotosBytes() {
        return photosBytes;
    }

    public void setPhotosBytes(List<byte[]> photosBytes) {
        this.photosBytes = photosBytes;
    }
}
