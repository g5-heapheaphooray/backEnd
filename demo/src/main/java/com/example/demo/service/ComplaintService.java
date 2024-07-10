package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateComplaintDTO;
import com.example.demo.dto.models.CleanComplaintDTO;
import com.example.demo.model.Complaint;
import com.example.demo.model.ComplaintMedia;
import com.example.demo.model.User;
import com.example.demo.repository.ComplaintRepository;

@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final MediaService mediaService;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository, MediaService mediaService) {
        this.complaintRepository = complaintRepository;
        this.mediaService = mediaService;
    }

    public CleanComplaintDTO getCleanComplaintDTO(Complaint c) {
        Set<ComplaintMedia> cms = c.getPhotos();
        List<String> cmsFp = new ArrayList<>();
        List<byte[]> cmsBytes = new ArrayList<>();
        for (ComplaintMedia cm : cms) {
            try {
                cmsBytes.add(mediaService.getMedia(cm.getFilepath()));
                cmsFp.add(cm.getFilepath());
            } catch (Exception ex) {
            }         
        }
        return new CleanComplaintDTO(c.getId(), c.getUser().getEmail(), c.getDateTime(), c.getTitle(), c.getDescription(), c.getStatus(), cmsFp);
    }

    public CleanComplaintDTO createComplaint(CreateComplaintDTO c, User user) {
        Complaint complaint = new Complaint();

        complaint.setUser(user);
        complaint.setTitle(c.getTitle());
        complaint.setDescription(c.getDescription());
        complaint.setDateTime(c.getDateTime());
        complaint.setStatus(c.getStatus());

        complaint = complaintRepository.save(complaint);
        return getCleanComplaintDTO(complaint);
    }

    public List<CleanComplaintDTO> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        List<CleanComplaintDTO> cleanComplaints = new ArrayList<>();
        for (Complaint c : complaints) {
            cleanComplaints.add(getCleanComplaintDTO(c));
        }
        return cleanComplaints;
    }

    public CleanComplaintDTO getComplaint(int complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);
        if (complaint == null) {
            return null;
        }
        return getCleanComplaintDTO(complaint);
    }

    public CleanComplaintDTO updateComplaint(CreateComplaintDTO c, int complaintId) {
        Complaint currentComplaint = complaintRepository.findById(complaintId).orElse(null);
        
        if (currentComplaint == null) {
            return null;
        }

        currentComplaint.setTitle(c.getTitle());
        currentComplaint.setDescription(c.getDescription());
        currentComplaint.setDateTime(c.getDateTime());
        currentComplaint.setStatus(c.getStatus());

        currentComplaint = complaintRepository.save(currentComplaint);
        return getCleanComplaintDTO(currentComplaint);
    }

    public CleanComplaintDTO deleteComplaint(int complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);

        if (complaint == null) {
            return null;
        }
        try {
            complaintRepository.delete(complaint);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return getCleanComplaintDTO(complaint);
    }

    public CleanComplaintDTO resolveComplaint(int complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);
        if (complaint == null) {
            return null;
        }
        complaint.setStatus("resolved");
        return getCleanComplaintDTO(complaintRepository.save(complaint));
    }
}
