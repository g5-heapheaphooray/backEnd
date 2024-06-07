package com.example.demo.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
        String hash = null;
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            hash = bytesToHex(hashbytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.password = hash;
        this.eventsPart = eventsPart;
        this.eventsOrg = eventsOrg;
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
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
        String hash = null;
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            hash = bytesToHex(hashbytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.password = hash;
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
