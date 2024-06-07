package com.example.demo.dto;

import com.example.demo.model.Event;
import jakarta.persistence.Column;

import java.util.List;

public class RegisterVolunteerDTO {
    private String email;
    private String fullName;
    private String password;
    private String contactNo;
    private char gender;
    private int age;

    public RegisterVolunteerDTO(String email, String fullName, String password, String contactNo, char gender, int age) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.contactNo = contactNo;
        this.gender = gender;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
