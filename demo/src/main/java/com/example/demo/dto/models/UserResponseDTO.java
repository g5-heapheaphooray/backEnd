package com.example.demo.dto.models;

import java.time.LocalDate;

public class UserResponseDTO {
    private String email;
    private String fullName;
    private int complainCount;
    private String contactNo;
    private char gender;
    private LocalDate dob;
    private double hours;
    private int points;
    private String location;
    private String website;
    private String description;
    private char userType;
    private boolean verified;
    private boolean blacklisted;
    private String pfpFilepath;

    public UserResponseDTO(String email, String fullName, int complainCount, String contactNo, char gender, LocalDate dob, double hours, int points, char userType, boolean blacklisted, String pfpFilepath) {
        this.email = email;
        this.fullName = fullName;
        this.complainCount = complainCount;
        this.contactNo = contactNo;
        this.gender = gender;
        this.dob = dob;
        this.hours = hours;
        this.points = points;
        this.userType = userType;
        this.blacklisted = blacklisted;
        this.pfpFilepath = pfpFilepath;
    }

    public UserResponseDTO(String email, String fullName, int complainCount, String contactNo, String location, String website, String description, boolean verified, boolean blacklisted, char userType, String pfpFilepath) {
        this.email = email;
        this.fullName = fullName;
        this.complainCount = complainCount;
        this.contactNo = contactNo;
        this.location = location;
        this.website = website;
        this.description = description;
        this.userType = userType;
        this.verified = verified;
        this.blacklisted = blacklisted;
        this.pfpFilepath = pfpFilepath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getComplainCount() {
        return complainCount;
    }

    public void setComplainCount(int complainCount) {
        this.complainCount = complainCount;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getUserType() {
        return userType;
    }

    public void setUserType(char userType) {
        this.userType = userType;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public String getPfpFilepath() {
        return pfpFilepath;
    }

    public void setPfpFilepath(String pfpFilepath) {
        this.pfpFilepath = pfpFilepath;
    }
}
