package com.example.demo.dto;

import com.example.demo.model.Event;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.util.List;

public class RegisterVolunteerDTO {
    private String email;
    private String fullName;
    private String password;
    private String contactNo;
    private char gender;
    private LocalDate dob;

    public RegisterVolunteerDTO(String email, String fullName, String password, String contactNo, char gender, LocalDate dob) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.contactNo = contactNo;
        this.gender = gender;
        this.dob = dob;
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
}
