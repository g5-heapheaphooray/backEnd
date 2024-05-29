package com.example.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("V")
public class Volunteer extends User {
    @Column(name = "gender")
    private char gender;

    @Column(name = "age")
    private int age;

    @Column(name = "hours")
    private double hours;

//    @ManyToMany
//    @JoinTable(
//            name = "events_part",
//            joinColumns = @JoinColumn(name = "user_email"),
//            inverseJoinColumns = @JoinColumn(name = "event_id"))
//    private List<Event> eventsPart;

    @Column(name = "points")
    private int points;

    public Volunteer() {}

    public Volunteer(String fullName, char gender, int age, String email, String contactNo, String password){
        super(fullName, email, contactNo, password, new ArrayList<>(), null);
        this.gender = gender;
        this.age = age;
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

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

//    public List<Event> getEventsPart() {
//        return eventsPart;
//    }
//
//    public void setEventsPart(List<Event> eventsPart) {
//        this.eventsPart = eventsPart;
//    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
