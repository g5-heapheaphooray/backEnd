package com.example.demo.dto.models;

public class CleanRewardsCategoryDTO {
    private int id;
    private String name;
    private int pointsNeeded;

    private String type;

    private String description;
    private int count;
    private String mediaFilepath;
    private byte[] mediaBytes;

    public CleanRewardsCategoryDTO(int id, String name, int pointsNeeded, String type, String description, int count, String mediaFilepath) {
        this.id = id;
        this.name = name;
        this.pointsNeeded = pointsNeeded;
        this.type = type;
        this.description = description;
        this.count = count;
        this.mediaFilepath = mediaFilepath;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMediaFilepath() {
        return mediaFilepath;
    }

    public void setMediaFilepath(String mediaFilepath) {
        this.mediaFilepath = mediaFilepath;
    }

    public byte[] getMediaBytes() {
        return mediaBytes;
    }

    public void setMediaBytes(byte[] mediaBytes) {
        this.mediaBytes = mediaBytes;
    }
}
