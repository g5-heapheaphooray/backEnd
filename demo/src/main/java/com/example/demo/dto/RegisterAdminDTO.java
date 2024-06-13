package com.example.demo.dto;

public class RegisterAdminDTO {
    private String email;
    private String fullName;
    private String password;
    private String contactNo;

    public RegisterAdminDTO(String email, String fullName, String password, String contactNo) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.contactNo = contactNo;
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

}
