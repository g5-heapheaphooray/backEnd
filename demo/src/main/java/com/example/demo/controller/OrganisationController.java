package com.example.demo.controller;

import com.example.demo.dto.models.CleanEventDTO;
import com.example.demo.dto.models.CleanOrganisationDTO;
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

import java.util.ArrayList;
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
    public ResponseEntity<CleanOrganisationDTO> updateVolunteer(@RequestBody Map<String, String> payload) {
        Organisation o = organisationService.updateDetails(payload.get("email"), payload);
        CleanOrganisationDTO co = new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), o.getPfp().getFilepath());
        return new ResponseEntity<>(co, HttpStatus.OK);
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity<CleanOrganisationDTO> getOrg(@PathVariable String orgId) {
        Organisation o = organisationService.getOrg(orgId);
        CleanOrganisationDTO co = new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), o.getPfp().getFilepath());
        return  new ResponseEntity<>(co, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<OrgListDTO> getAllOrg() {
        List<CleanOrganisationDTO> cleanOrgs = organisationService.getAllOrg();
        OrgListDTO res = new OrgListDTO(cleanOrgs);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/events/{orgId}")
    public ResponseEntity<EventsListDTO> orgEvents(@PathVariable String orgId) {
        Organisation o = organisationService.getOrg(orgId);
        List<Event> events = eventService.getOrgEvents(o);
        List<CleanEventDTO> cleanEvents = new ArrayList<>();
        for (Event e : events) {
            cleanEvents.add(eventService.getCleanEvent(e));
        }
        EventsListDTO res = new EventsListDTO(cleanEvents);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
