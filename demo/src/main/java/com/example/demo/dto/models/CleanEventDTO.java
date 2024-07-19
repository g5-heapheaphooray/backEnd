package com.example.demo.dto.models;

import com.example.demo.model.EventMedia;
import com.example.demo.model.Organisation;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class CleanEventDTO {
    private int id;
    private String name;
    private LocalDate date;
    private LocalTime startTime;

    private LocalTime endTime;

    private String organisation_id;

//    private List<User> participants;

    private int neededManpowerCount;
    private int currentManpowerCount;
    private String location;
    private String description;

    private String type;

    private String address;
    private List<String> skills;
    private List<String> causes;
    private List<String> photosFilepaths;
//    private String coverPhotoFilepath;

    public CleanEventDTO(int id, String name, LocalDate date, LocalTime startTime, LocalTime endTime, String organisation_id, int neededManpowerCount, int currentManpowerCount, String location, String description, String type, String address, 
    List<String> skills, List<String> causes, List<String> photosFilepaths) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organisation_id = organisation_id;
        this.neededManpowerCount = neededManpowerCount;
        this.currentManpowerCount = currentManpowerCount;
        this.location = location;
        this.description = description;
        this.type = type;
        this.address = address;
        this.skills = skills;
        this.causes = causes;
        this.photosFilepaths = photosFilepaths;
//        this.coverPhotoFilepath = coverPhotoFilepath;
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

    public String getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(String organisation_id) {
        this.organisation_id = organisation_id;
    }

    public int getNeededManpowerCount() {
        return neededManpowerCount;
    }

    public void setNeededManpowerCount(int neededManpowerCount) {
        this.neededManpowerCount = neededManpowerCount;
    }

    public int getCurrentManpowerCount() {
        return currentManpowerCount;
    }

    public void setCurrentManpowerCount(int currentManpowerCount) {
        this.currentManpowerCount = currentManpowerCount;
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

    public List<String> getPhotosFilepaths() {
        return photosFilepaths;
    }

    public void setPhotosFilepaths(List<String> photosFilepaths) {
        this.photosFilepaths = photosFilepaths;
    }

//    public String getCoverPhotoFilepath() {
//        return coverPhotoFilepath;
//    }
//
//    public void setCoverPhotoFilepath(String coverPhotoFilepath) {
//        this.coverPhotoFilepath = coverPhotoFilepath;
//    }

    
}
