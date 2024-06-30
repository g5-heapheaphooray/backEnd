package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CreateOppDTO {
    private String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern="HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern="HH:mm")
    private LocalTime endTime;

//    private String token; // retrieve from token
    private int manpowerCount;
    private String location;
    private String description;
    private String type;
    private String address;
    private List<String> skills;
    private List<String> causes;

    public CreateOppDTO(String name, LocalDate date, LocalTime startTime, LocalTime endTime, int manpowerCount, String location, String description, String type, String address, List<String> skills, List<String> causes) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
//        this.organisationEmail = organisationEmail;
        this.manpowerCount = manpowerCount;
        this.location = location;
        this.description = description;
        this.type = type;
        this.address = address;
        this.skills = skills;
        this.causes = causes;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getCauses() {
        return causes;
    }   

    public void setCauses(List<String> causes) {
        this.causes = causes;
    }
}
