package com.example.demo.model;

import java.util.List;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id 
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private char gender;

    @Column(name = "age")
    private int age;

    @Column(name = "admin")
    private boolean admin;

    @Column(name = "hours")
    private double hours;

    @ManyToMany
    @JoinTable(
        name = "events_part",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> eventsPart;

    @Column(name = "complain_count")
    private int complainCount;

    @Column(name = "password")
    private String password;

    @Column(name = "points")
    private int points;

    @Column(name = "contact_no")
    private long contactNo;

    public User(){
    }

    public User(String fullName, char gender, int age, String email, long contactNo, String password){
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public List<Event> getEventsPart() {
        return eventsPart;
    }

    public void setEventsPart(List<Event> eventsPart) {
        this.eventsPart = eventsPart;
    }

    public int getComplainCount() {
        return complainCount;
    }

    public void setComplainCount(int complainCount) {
        this.complainCount = complainCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getContactNo() {
        return contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    
    
}
