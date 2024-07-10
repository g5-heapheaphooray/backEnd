package com.example.demo.dto;

import java.util.List;

import com.example.demo.dto.models.CleanComplaintDTO;

public class ComplaintsListDTO {
    public List<CleanComplaintDTO> complaints;

    public ComplaintsListDTO(List<CleanComplaintDTO> complaints) {
        this.complaints = complaints;
    }

    public List<CleanComplaintDTO> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<CleanComplaintDTO> complaints) {
        this.complaints = complaints;
    }
}
