package com.example.demo.model;

import jakarta.persistence.*;

//@Entity
//@Table(name = "media")
@MappedSuperclass
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "filename")
    private String filename;

    @Column(name = "filepath")
    private String filepath;


    public Media() {
    }

    public Media(String filename, String filepath) {
        this.filename = filename;
        this.filepath = filepath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    
}
