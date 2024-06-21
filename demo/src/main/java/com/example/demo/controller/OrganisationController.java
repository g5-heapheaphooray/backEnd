package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Organisation;
import com.example.demo.service.OrganisationService;
import com.example.demo.model.Event;
import com.example.demo.service.EventService;
import com.example.demo.dto.OrgListDTO;
import com.example.demo.dto.EventsListDTO;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/organisation")
public class OrganisationController {
    
    private final OrganisationService organisationService;
    private final EventService eventService;

    @Autowired
    public OrganisationController(OrganisationService organisationService, EventService eventService) {
        this.organisationService = organisationService;
        this.eventService = eventService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<Organisation> createOrganisation(@RequestBody Organisation organisation) {
//        Organisation newOrganisation = organisationService.createOrganisation(organisation);
//        return new ResponseEntity<>(newOrganisation, HttpStatus.CREATED);
//    }

    @PutMapping("/updateDetails")
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<Organisation> updateVolunteer(@RequestBody Map<String, String> payload) {
        Organisation o = organisationService.updateDetails(payload.get("email"), payload);
        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity<Organisation> getOrg(@PathVariable String orgId) {
        Organisation u = organisationService.getOrg(orgId);
        return  new ResponseEntity<>(u, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<OrgListDTO> getAllOrg() {
        List<Organisation> orgs = organisationService.getAllOrg();
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
