package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Volunteer;

public class VolListDTO {
    public List<Volunteer> vols;

    public VolListDTO(List<Volunteer> vols) {
        this.vols = vols;
    }

    public List<Volunteer> getVols() {
        return vols;
    }

    public void setVols(List<Volunteer> vols) {
        this.vols = vols;
    }


}