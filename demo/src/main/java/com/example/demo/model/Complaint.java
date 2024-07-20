package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;

@Entity
@Table(name = "complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "datetime")
    private LocalDateTime dateTime;
    
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "complaint")
    private Set<ComplaintMedia> photos;

    // pending, revoked, resolved
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "target_id", nullable = true)
    private User complainee;

    public Complaint() {
        photos = new HashSet<>();
    }

    public Complaint(User user, LocalDateTime dateTime, String title, String description, String status, User complainee) {
        this.user = user;
        this.dateTime = dateTime;
        this.title = title;
        this.description = description;
        photos = new HashSet<>();
        this.status = status;
        this.complainee = complainee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ComplaintMedia> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<ComplaintMedia> photos) {
        this.photos = photos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getComplainee() {
        return complainee;
    }

    public void setComplainee(User complainee) {
        this.complainee = complainee;
    }
}
