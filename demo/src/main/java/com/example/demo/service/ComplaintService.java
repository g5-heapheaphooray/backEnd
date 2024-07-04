package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateComplaintDTO;
import com.example.demo.model.Complaint;
import com.example.demo.model.User;
import com.example.demo.repository.ComplaintRepository;

@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Complaint createComplaint(CreateComplaintDTO c, User user) {
        Complaint complaint = new Complaint();

        complaint.setUser(user);
        complaint.setTitle(c.getTitle());
        complaint.setDescription(c.getDescription());
        complaint.setDateTime(c.getDateTime());
        complaint.setStatus(c.getStatus());

        return complaintRepository.save(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaint(int complaintId) {
        return complaintRepository.findById(complaintId).orElse(null);
    }

    public Complaint updateComplaint(CreateComplaintDTO c, int complaintId) {
        Complaint currentComplaint = complaintRepository.findById(complaintId).orElse(null);
        
        if (currentComplaint == null) {
            return null;
        }

        currentComplaint.setTitle(c.getTitle());
        currentComplaint.setDescription(c.getDescription());
        currentComplaint.setDateTime(c.getDateTime());
        currentComplaint.setStatus(c.getStatus());

        return complaintRepository.save(currentComplaint);
    }

    public Complaint deleteComplaint(int complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            return null;
        }
        try {
            complaintRepository.delete(complaint);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return complaint;
    }

    
}
