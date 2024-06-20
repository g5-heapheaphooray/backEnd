package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Organization;
import com.example.demo.service.OrganizationService;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/organization")
public class OrganizationController {
    
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
//        Organization newOrganization = organizationService.createOrganization(organization);
//        return new ResponseEntity<>(newOrganization, HttpStatus.CREATED);
//    }

    @PutMapping("/updateDetails")
    public ResponseEntity<Organization> updateVolunteer(@RequestBody Map<String, String> payload) {
        Organization o = organizationService.updateDetails(payload.get("email"), payload);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping("/getOrganisation")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<Organization> getOrg(@RequestBody String email) {
        Organization u = organizationService.getOrg(email);
        return  new ResponseEntity<>(u, HttpStatus.OK);
    }
}
