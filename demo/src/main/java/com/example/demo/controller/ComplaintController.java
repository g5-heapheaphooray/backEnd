package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ComplaintsListDTO;
import com.example.demo.dto.CreateComplaintDTO;
import com.example.demo.dto.models.CleanComplaintDTO;
import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.service.ComplaintService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/complaint")
public class ComplaintController {
    
    private final ComplaintService complaintService;
    
    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CleanComplaintDTO> createComplaint(@RequestBody CreateComplaintDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        CleanComplaintDTO cc = complaintService.createComplaint(dto, user);
        if (cc != null) {
            return new ResponseEntity<>(cc, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }   

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplaintsListDTO> getAllComplaints() {
        List<CleanComplaintDTO> cc = complaintService.getAllComplaints();
        ComplaintsListDTO ccd = new ComplaintsListDTO(cc);
        return new ResponseEntity<>(ccd, HttpStatus.OK);
    }

    @GetMapping("/get/{complaintId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CleanComplaintDTO> getComplaint(@PathVariable int complaintId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        CleanComplaintDTO complaint = complaintService.getComplaint(complaintId);
        if (user instanceof Admin || complaint.getUserId().equals(user.getEmail())) {
            return new ResponseEntity<>(complaintService.getComplaint(complaintId), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{complaintId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CleanComplaintDTO> updateComplaint(@PathVariable int complaintId, @RequestBody CreateComplaintDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        CleanComplaintDTO complaint = complaintService.getComplaint(complaintId);
        if (user instanceof Admin || complaint.getUserId().equals(user.getEmail())) {
            return new ResponseEntity<>(complaintService.updateComplaint(dto, complaintId), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{complaintId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CleanComplaintDTO> deleteComplaint(@PathVariable int complaintId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        CleanComplaintDTO complaint = complaintService.getComplaint(complaintId);
        if (user instanceof Admin || complaint.getUserId().equals(user.getEmail())) {
            return new ResponseEntity<>(complaintService.deleteComplaint(complaintId), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/resolve/{complaintId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CleanComplaintDTO> resolveComplaint(@PathVariable int complaintId) {
        return new ResponseEntity<>(complaintService.resolveComplaint(complaintId), HttpStatus.OK);
    }
}
