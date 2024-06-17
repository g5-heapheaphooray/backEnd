package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.example.demo.model.RoleEnum;
import com.example.demo.repository.RoleRepository;

@Entity
@DiscriminatorValue("V")
public class Volunteer extends User {
    @Column(name = "gender")
    private char gender;

    @Column(name = "dob")
    private LocalDate dob;

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

    public Volunteer(String fullName, char gender, LocalDate dob, String email, String contactNo, String password, Role role){
//        super(fullName, email, contactNo, password, new ArrayList<>(), null, roleRepository.findByName(RoleEnum.VOLUNTEER));
        super(fullName, email, contactNo, password, new HashSet<>(), null, role);
        this.gender = gender;
        this.dob = dob;
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

    // public boolean addEventPart(Event e) {
    //     if (!isAccountNonLocked() || getEventsPart() == null) {
    //         return false;
    //     }
    //     for (Event event : getEventsPart()) {
    //         if (event.getDate().equals(e.getDate())) {
    //             return false;
    //         }
    //     }
    //     List<Event> currentE = getEventsPart();
    //     currentE.add(e);
    //     setEventsPart(currentE);
    //     return true;
    // }

}
