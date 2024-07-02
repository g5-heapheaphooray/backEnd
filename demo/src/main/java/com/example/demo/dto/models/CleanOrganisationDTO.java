package com.example.demo.dto.models;

public class CleanOrganisationDTO {
    private String email;
    private String fullName;
    private int complainCount;
    private String contactNo;
    private String location;
    private String website;
    private String description;
    private String pfp_filepath;

    public CleanOrganisationDTO(String email, String fullName, int complainCount, String contactNo, String location, String website, String description, String pfp_filepath) {
        this.email = email;
        this.fullName = fullName;
        this.complainCount = complainCount;
        this.contactNo = contactNo;
        this.location = location;
        this.website = website;
        this.description = description;
        this.pfp_filepath = pfp_filepath;
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

    public String getPfp_filepath() {
        return pfp_filepath;
    }

    public void setPfp_filepath(String pfp_filepath) {
        this.pfp_filepath = pfp_filepath;
    }
}