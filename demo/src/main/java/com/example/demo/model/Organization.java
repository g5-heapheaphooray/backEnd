package com.example.demo.model;

import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("O")
public class Organization extends User {

//    @Id
//    private String email;

//    @Column(name = "name")
//    private String name;

//    @Column(name = "password")
//    private String password;

//    @Column(name = "contact_no")
//    private String contactNo;

    @Column(name = "location")
    private String location;

    @Column(name = "website")
    private String website;

    @Column(name = "description")
    private String description;

    @Column(name = "verified")
    private boolean verified;

    public Organization(){
    }

    public Organization(String email, String name, String password, String contactNo, String location, String website, String description){
        super(email, name, password, contactNo, null, new ArrayList<>());
        this.location = location;
        this.website = website;
        this.description = description;
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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    
    
}
