package com.example.demo.controller;

import com.example.demo.dto.models.CleanEventDTO;
import com.example.demo.dto.models.CleanOrganisationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Organisation;
import com.example.demo.model.User;
import com.example.demo.service.OrganisationService;
import com.example.demo.service.EventService;
import com.example.demo.dto.OrgListDTO;
import com.example.demo.dto.RegisterOrganisationDTO;
import com.example.demo.dto.EventsListDTO;

import java.util.List;

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

    @PutMapping("/updateDetails")
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<CleanOrganisationDTO> updateOrganisation(@RequestBody RegisterOrganisationDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Organisation o = organisationService.updateDetails(dto, user);
        CleanOrganisationDTO co = organisationService.getCleanOrg(o);
        return new ResponseEntity<>(co, HttpStatus.OK);
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity<CleanOrganisationDTO> getOrg(@PathVariable String orgId) {
        CleanOrganisationDTO o = organisationService.getCleanOrg(orgId);
        return  new ResponseEntity<>(o, HttpStatus.OK);
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
        List<CleanEventDTO> cleanEvents = eventService.getOrgEvents(o);
        EventsListDTO res = new EventsListDTO(cleanEvents);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
