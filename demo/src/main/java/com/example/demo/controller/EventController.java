package com.example.demo.controller;

import com.example.demo.dto.CreateOppDTO;
import com.example.demo.dto.EventsListDTO;
import com.example.demo.model.Organization;
import com.example.demo.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Event;
import com.example.demo.service.EventService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/event")
public class EventController {
    
    private final EventService eventService;
    private final OrganizationService organizationService;

    @Autowired
    public EventController(EventService eventService, OrganizationService organizationService) {
        this.eventService = eventService;
        this.organizationService = organizationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@RequestBody CreateOppDTO dto) {
//        System.out.println(dto.getOrganizationEmail());
//        System.out.println(dto);
        Organization o = organizationService.getOrg(dto.getOrganizationEmail());
        Event e = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), o, dto.getManpowerCount(), dto.getLocation(), dto.getDescription(), dto.getType());
        Event newEvent = eventService.createEvent(e);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<EventsListDTO> allEvents() {
        List<Event> events = eventService.getAllEvents();
        EventsListDTO res = new EventsListDTO(events);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{orgId}")
    public ResponseEntity<EventsListDTO> orgEvents(@PathVariable String orgId) {
        Organization o = organizationService.getOrg(orgId);
        List<Event> events = eventService.getOrgEvents(o);
        EventsListDTO res = new EventsListDTO(events);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
