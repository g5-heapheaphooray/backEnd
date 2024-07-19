package com.example.demo.model;

import java.util.HashSet;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("O")
public class Organisation extends User {
    @Column(name = "location")
    private String location;

    @Column(name = "website")
    private String website;

    @Column(name = "description")
    private String description;

    @Column(name = "verified")
    private boolean verified;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private PfpMedia pfp;

    public Organisation(){
    }

    public Organisation(String email, String name, String password, String contactNo, String location, String website, String description, Role role){
        super(name, email, contactNo, password, null, new HashSet<>(), role);
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

    @Override
    public boolean isEnabled() { // verified
        return verified;
    }    
}
