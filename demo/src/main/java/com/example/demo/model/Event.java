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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "event")
public class Event {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "start_time")
    @JsonFormat(pattern="HH:mm")
    private LocalTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern="HH:mm")
    private LocalTime endTime;

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

    public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime, Organization organization, int manpowerCount, String location, String description, String type){
        this.id = String.format("%s-%s", organization.getEmail(), name);
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organization = organization;
        this.manpowerCount = manpowerCount;
        this.description = description;
        this.type = type;
        this.eventOver = false;
        this.participants = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
