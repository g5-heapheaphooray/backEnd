package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("U")
public class User {

    @Id 
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "complain_count")
    // both volunteer and organisation will have but the limit diff ig
    private int complainCount;


    @Column(name = "contact_no")
    private String contactNo;


    @ManyToMany
    @JoinTable(
            name = "events_part",
            joinColumns = @JoinColumn(name = "user_email"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> eventsPart;
    // only applies to Volunteer subclass
    // if Organisation subclass, this field should be null

    @OneToMany(mappedBy = "organization")
    private List<Event> eventsOrg;
    // only applies to Organisation subclass
    // if Volunteer subclass, this field should be null

    public User(){
    }

    public User(String fullName, String email, String contactNo, String password, List<Event> eventsPart, List<Event> eventsOrg){
        this.fullName = fullName;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
        this.eventsPart = eventsPart;
        this.eventsOrg = eventsOrg;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public List<Event> getEventsPart() {
        return eventsPart;
    }

    public void setEventsPart(List<Event> eventsPart) {
        this.eventsPart = eventsPart;
    }

    public List<Event> getEventsOrg() {
        return eventsOrg;
    }

    public void setEventsOrg(List<Event> eventsOrg) {
        this.eventsOrg = eventsOrg;
    }
}
