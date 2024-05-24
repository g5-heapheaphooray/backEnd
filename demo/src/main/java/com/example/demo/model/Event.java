package com.example.demo.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "start_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTime;

    @Column(name = "hours")
    private double hours;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToMany(mappedBy = "eventsPart")
    private List<User> participants;

    @Column(name = "event_over")
    private boolean eventOver;

    @Column(name = "manpower_count")
    private int manpowerCount;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    public Event(){
    }

    public Event(Date startTime, Date endTime, Organization organization, double hours, int manpowerCount, String description, String type){
        this.startTime = startTime;
        this.endTime = endTime;
        this.organization = organization;
        this.hours = hours;
        this.manpowerCount = manpowerCount;
        this.description = description;
        this.type = type;
        this.eventOver = false;
        this.participants = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public boolean isEventOver() {
        return eventOver;
    }

    public void setEventOver(boolean eventOver) {
        this.eventOver = eventOver;
    }

    public int getManpowerCount() {
        return manpowerCount;
    }

    public void setManpowerCount(int manpowerCount) {
        this.manpowerCount = manpowerCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    
}
