package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Organization;
import com.example.demo.service.OrganizationService;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;
import com.example.demo.dto.OrgListDTO;
import com.example.demo.dto.EventsListDTO;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/organization")
public class OrganizationController {
    
    private final OrganizationService organizationService;
    private final EventService eventService;

    @Autowired
    public OrganizationController(OrganizationService organizationService, EventService eventService) {
        this.organizationService = organizationService;
        this.eventService = eventService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
//        Organization newOrganization = organizationService.createOrganization(organization);
//        return new ResponseEntity<>(newOrganization, HttpStatus.CREATED);
//    }

    @PutMapping("/updateDetails")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<Organization> updateVolunteer(@RequestBody Map<String, String> payload) {
        Organization o = organizationService.updateDetails(payload.get("email"), payload);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity<Organization> getOrg(@PathVariable String orgId) {
        Organization u = organizationService.getOrg(orgId);
        return  new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<OrgListDTO> getAllOrg() {
        List<Organization> orgs = organizationService.getAllOrg();
        OrgListDTO res = new OrgListDTO(orgs);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/events/{orgId}")
    public ResponseEntity<EventsListDTO> orgEvents(@PathVariable String orgId) {
        List<Event> events = eventService.getOrgEvents(orgId);
        EventsListDTO res = new EventsListDTO(events);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
