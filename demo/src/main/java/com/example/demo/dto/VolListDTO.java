package com.example.demo.dto;

import java.util.List;

import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.model.Volunteer;

public class VolListDTO {
    public List<CleanVolunteerDTO> vols;

    public VolListDTO(List<CleanVolunteerDTO> vols) {
        this.vols = vols;
    }

    public List<CleanVolunteerDTO> getVols() {
        return vols;
    }

    public void setVols(List<CleanVolunteerDTO> vols) {
        this.vols = vols;
    }


}