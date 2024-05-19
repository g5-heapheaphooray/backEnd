package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Organization;
import com.example.demo.service.OrganizationService;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationController {
    
    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        Organization newOrganization = organizationService.createOrganization(organization);
        return new ResponseEntity<>(newOrganization, HttpStatus.CREATED);
    }
}
