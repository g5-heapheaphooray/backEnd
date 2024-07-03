package com.example.demo.dto.models;

import jakarta.persistence.Column;

public class CleanRewardsDTO {
    private int id;
    private String name;
    private int pointsNeeded;

    private String type;

    private String description;

    public CleanRewardsDTO(int id, String name, int pointsNeeded, String type, String description) {
        this.id = id;
        this.name = name;
        this.pointsNeeded = pointsNeeded;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
