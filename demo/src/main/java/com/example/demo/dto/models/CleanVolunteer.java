//package com.example.demo.dto.models;
//
//public class CleanVolunteer {
//    private String email;
//    private String fullName;
//    // both volunteer and organisation will have but the limit diff ig
//    private int complainCount;
//    private String contactNo;
//    private char gender;
//    private LocalDate dob;
//    private double hours;
//    private int points;
//
//    public UserResponseDTO(String message, int code, String email, String fullName, int complainCount, String contactNo, char gender, LocalDate dob, double hours, int points) {
//        super(message, code);
//        this.email = email;
//        this.fullName = fullName;
//        this.complainCount = complainCount;
//        this.contactNo = contactNo;
//        this.gender = gender;
//        this.dob = dob;
//        this.hours = hours;
//        this.points = points;
//    }
//
//    public UserResponseDTO(String message, int code, String email, String fullName, int complainCount, String contactNo, String location, String website, String description, char userType) {
//        super(message, code);
//        this.email = email;
//        this.fullName = fullName;
//        this.complainCount = complainCount;
//        this.contactNo = contactNo;
//        this.location = location;
//        this.website = website;
//        this.description = description;
//        this.userType = userType;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public int getComplainCount() {
//        return complainCount;
//    }
//
//    public void setComplainCount(int complainCount) {
//        this.complainCount = complainCount;
//    }
//
//    public String getContactNo() {
//        return contactNo;
//    }
//
//    public void setContactNo(String contactNo) {
//        this.contactNo = contactNo;
//    }
//
//    public char getGender() {
//        return gender;
//    }
//
//    public void setGender(char gender) {
//        this.gender = gender;
//    }
//
//    public LocalDate getDob() {
//        return dob;
//    }
//
//    public void setDob(LocalDate dob) {
//        this.dob = dob;
//    }
//
//    public double getHours() {
//        return hours;
//    }
//
//    public void setHours(double hours) {
//        this.hours = hours;
//    }
//
//    public int getPoints() {
//        return points;
//    }
//
//    public void setPoints(int points) {
//        this.points = points;
//    }
//}