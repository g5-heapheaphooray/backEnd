package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateOppDTO {
    private String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern="HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern="HH:mm")
    private LocalTime endTime;

//    private String organizationEmail; // retrieve from token
    private int manpowerCount;
    private String location;
    private String description;
    private String type;

    public CreateOppDTO(String name, LocalDate date, LocalTime startTime, LocalTime endTime, int manpowerCount, String location, String description, String type) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
//        this.organizationEmail = organizationEmail;
        this.manpowerCount = manpowerCount;
        this.location = location;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getManpowerCount() {
        return manpowerCount;
    }

    public void setManpowerCount(int manpowerCount) {
        this.manpowerCount = manpowerCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CreateOppDTO{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", manpowerCount=" + manpowerCount +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
