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

import com.example.demo.dto.CreateComplaintDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.Admin;
import com.example.demo.model.Complaint;
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
    public ResponseEntity<ResponseDTO> createComplaint(@RequestBody CreateComplaintDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("complaint creation unsucessful", 400);
        if (user != null) {
            complaintService.createComplaint(dto, user);
            res = new ResponseDTO("complaint created successfully", 200);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
    
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }   

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return new ResponseEntity<>(complaintService.getAllComplaints(), HttpStatus.OK);
    }

    @GetMapping("/get/{complaintId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Complaint> getComplaint(@PathVariable int complaintId) {
        return new ResponseEntity<>(complaintService.getComplaint(complaintId), HttpStatus.OK);
    }

    @PutMapping("/update/{complaintId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Complaint> updateComplaint(@PathVariable int complaintId, @RequestBody CreateComplaintDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Complaint complaint = complaintService.getComplaint(complaintId);
        if (user instanceof Admin || complaint.getUser().getEmail().equals(user.getEmail())) {
            return new ResponseEntity<>(complaintService.updateComplaint(dto, complaintId), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{complaintId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Complaint> deleteComplaint(@PathVariable int complaintId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Complaint complaint = complaintService.getComplaint(complaintId);
        if (user instanceof Admin || complaint.getUser().getEmail().equals(user.getEmail())) {
            return new ResponseEntity<>(complaintService.deleteComplaint(complaintId), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
