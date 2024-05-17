package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reward")
public class Reward {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "points_needed")
    private int pointsNeeded;

    @Column(name = "barcode_serial_no")
    private String barcodeSerialNo;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    public Reward(){
    }

    public Reward(String name, int pointsNeeded, String barcodeSerialNo, String type, String description){
        this.name = name;
        this.pointsNeeded = pointsNeeded;
        this.barcodeSerialNo = barcodeSerialNo;
        this.type = type;
        this.description = description;
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

    public int getPointsNeeded() {
        return pointsNeeded;
    }

    public void setPointsNeeded(int pointsNeeded) {
        this.pointsNeeded = pointsNeeded;
    }

    public String getBarcodeSerialNo() {
        return barcodeSerialNo;
    }

    public void setBarcodeSerialNo(String barcodeSerialNo) {
        this.barcodeSerialNo = barcodeSerialNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

}
