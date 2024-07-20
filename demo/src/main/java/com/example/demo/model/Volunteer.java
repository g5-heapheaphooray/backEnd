package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@DiscriminatorValue("V")
public class Volunteer extends User {
    @Column(name = "gender")
    private char gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "hours")
    private double hours;

    @Column(name = "points")
    private int points;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "volunteer")
    private Set<RewardBarcode> redeemedRewards;

    public Volunteer() {}

    public Volunteer(String fullName, char gender, LocalDate dob, String email, String contactNo, String password, Role role){
        super(fullName, email, contactNo, password, new HashSet<>(), null, role,  "Profile-Media/vol-default.png");
        this.gender = gender;
        this.dob = dob;
        this.redeemedRewards = new HashSet<>();
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

    public Set<RewardBarcode> getRedeemedRewards() {
        return redeemedRewards;
    }

    public void setRedeemedRewards(Set<RewardBarcode> redeemedRewards) {
        this.redeemedRewards = redeemedRewards;
    }
}
