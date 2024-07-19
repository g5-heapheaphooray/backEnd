package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // private String id;

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

//    @Column(name = "organisation_id")
//    private String organisation;

    @ManyToOne
    @JoinColumn(name="organisation_id", nullable=false)
    private Organisation organisation;

    @JsonIgnore
    @ManyToMany(mappedBy = "eventsPart")
    private Set<User> participants;

    @Column(name = "needed_manpower_count")
    private int neededManpowerCount;

    @Column(name = "current_manpower_count")
    private int currentManpowerCount;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "address")
    private String address;

    @Column(name = "points")
    private int points;

//    @Column(name = "photos")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<EventMedia> photos;

    @ElementCollection
    @CollectionTable(
            name="event_skills",
            joinColumns=@JoinColumn(name="event_id")
    )
    private List<String> skills;

    @ElementCollection
    @CollectionTable(
            name="event_causes",
            joinColumns=@JoinColumn(name="event_id")
    )
    private List<String> causes;


    public Event(){
    }

    public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime, Organisation organisation, int neededManpowerCount, String location, String description, String type, String address, List<String> skills, List<String> causes){
        // this.id = String.format("%s-%s", organisation.getEmail(), name);
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organisation = organisation;
        this.neededManpowerCount = neededManpowerCount;
        this.description = description;
        this.type = type;
        this.location = location;
        this.address = address;
        this.participants = new HashSet<>();
        this.skills = skills;
        this.causes = causes;
        this.photos = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public int getNeededManpowerCount() {
        return neededManpowerCount;
    }

    public void setNeededManpowerCount(int manpowerCount) {
        this.neededManpowerCount = manpowerCount;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCurrentManpowerCount() {
        return currentManpowerCount;
    }

    public void setCurrentManpowerCount(int currentManpowerCount) {
        this.currentManpowerCount = currentManpowerCount;
    }

    public void incCurrentManpowerCount() {
        this.neededManpowerCount-=1;
        this.currentManpowerCount+=1;
    }

    public void decCurrentManpowerCount() {
        this.neededManpowerCount+=1;
        this.currentManpowerCount-=1;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getCauses() {
        return causes;
    }

    public void setCauses(List<String> causes) {
        this.causes = causes;
    }

    public Set<EventMedia> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<EventMedia> photos) {
        this.photos = photos;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
