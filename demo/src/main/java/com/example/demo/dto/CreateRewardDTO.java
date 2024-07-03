package com.example.demo.dto;

public class CreateRewardDTO {
    private String name;
    private int pointsNeeded;
    private String type;
    private String description;
    
    public CreateRewardDTO(String name, int pointsNeeded, String type, String description) {
        this.name = name;
        this.pointsNeeded = pointsNeeded;
        this.type = type;
        this.description = description;
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
    
    
}
