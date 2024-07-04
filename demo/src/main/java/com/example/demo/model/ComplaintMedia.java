package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "complaint_media")
public class ComplaintMedia extends Media {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    public ComplaintMedia() {}

    public ComplaintMedia(String filename, String filepath, Complaint complaint) {
        super(filename, filepath);
        this.complaint = complaint;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }  
}
