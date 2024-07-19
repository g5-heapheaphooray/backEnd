package com.example.demo.dto;

public class RegisterOrganisationDTO {
    private String email;
    private String fullName;
    private String password;
    private String contactNo;
    private String location;
    private String website;
    private String description;

    public RegisterOrganisationDTO(String email, String fullName, String password, String contactNo, String location, String website, String description) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.contactNo = contactNo;
        this.location = location;
        this.website = website;
        this.description = description;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
