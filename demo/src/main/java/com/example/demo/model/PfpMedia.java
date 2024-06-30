package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pfp_media")
public class PfpMedia extends Media {
//    @Column(name = "user")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pfp")
    private User user;

    public PfpMedia() {
    }

    public PfpMedia(String filename, String filepath, User user) {
        super(filename, filepath);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
