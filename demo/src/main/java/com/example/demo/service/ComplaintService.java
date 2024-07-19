package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final MailService mailService;

    @Value("${admin.complaintemail}")
    private String gmailEmail;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository, MediaService mediaService, MailService mailService) {
        this.complaintRepository = complaintRepository;
        this.mediaService = mediaService;
        this.mailService = mailService;
    }

    public CleanComplaintDTO getCleanComplaintDTO(Complaint c) {
        Set<ComplaintMedia> cms = c.getPhotos();
        List<String> cmsFp = new ArrayList<>();
        for (ComplaintMedia cm : cms) {
            try {
                cmsFp.add(mediaService.getObjectUrl(cm.getFilepath()));
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

        String msg = """
A new complaint has been made by %s with the following title:
%s
and the following description:
%s
                """.formatted(user.getEmail(), c.getTitle(), c.getDescription());
        mailService.sendMail(gmailEmail, "New Complaint Made: %s".formatted(c.getTitle()), msg);

        return getCleanComplaintDTO(complaint);
    }

    public void createComplaintNoMail(CreateComplaintDTO c, User user) {
        Complaint complaint = new Complaint(user, c.getDateTime(), c.getTitle(), c.getDescription(), c.getStatus());
        complaint = complaintRepository.save(complaint);
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

    public Complaint getUncleanComplaint(int complaintId) {
        return complaintRepository.findById(complaintId).orElse(null);
    }
}
