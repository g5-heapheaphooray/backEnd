package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public class CreateRewardDTO {
    private String name;
    private int pointsNeeded;
    private String type;
    private String description;
    private int count;
    private MultipartFile media;
    
    public CreateRewardDTO(String name, int pointsNeeded, String type, String description, int count, MultipartFile media) {
        this.name = name;
        this.pointsNeeded = pointsNeeded;
        this.type = type;
        this.description = description;
        this.count = count;
        this.media = media;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPointsNeeded() {
        return pointsNeeded;
    }

    public void setPointsNeeded(int pointsNeeded) {
        this.pointsNeeded = pointsNeeded;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MultipartFile getMedia() {
        return media;
    }

    public void setMedia(MultipartFile media) {
        this.media = media;
    }
}
