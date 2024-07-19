package com.example.demo.dto.models;

import java.time.LocalDate;

public class CleanVolunteerDTO {
    private String email;
    private String fullName;
    private int complainCount;
    private String contactNo;
    private char gender;
    private LocalDate dob;
    private double hours;
    private int points;
    private String pfp_filepath;

    public CleanVolunteerDTO(String email, String fullName, int complainCount, String contactNo, char gender, LocalDate dob, double hours, int points, String pfp_filepath) {
        this.email = email;
        this.fullName = fullName;
        this.complainCount = complainCount;
        this.contactNo = contactNo;
        this.gender = gender;
        this.dob = dob;
        this.hours = hours;
        this.points = points;
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

    public String getPfp_filepath() {
        return pfp_filepath;
    }

    public void setPfp_filepath(String pfp_filepath) {
        this.pfp_filepath = pfp_filepath;
    }
}