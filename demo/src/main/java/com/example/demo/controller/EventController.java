package com.example.demo.controller;

import com.example.demo.dto.CreateOppDTO;
import com.example.demo.dto.EventsListDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.Organization;
import com.example.demo.model.User;
import com.example.demo.service.OrganizationService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Event;
import com.example.demo.service.EventService;

import java.util.Arrays;
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
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<ResponseDTO> createEvent(@RequestBody CreateOppDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("event creation unsucessful", 400);
        if (user instanceof Organization) {
//            Organization o = (Organization) user;
            Event e = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), (Organization) user, dto.getManpowerCount(), dto.getLocation(), dto.getDescription(), dto.getType());
            System.out.println(e.getName());
            Event newEvent = eventService.createEvent(e, user);
            if (newEvent != null) {
                res = new ResponseDTO("event creation sucessful", 200);
            }
        }
//        System.out.println(dto.getOrganizationEmail());
//        System.out.println(dto);
//        Organization o = organizationService.getOrg(dto.getOrganizationEmail());
//        Event e = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), o, dto.getManpowerCount(), dto.getLocation(), dto.getDescription(), dto.getType());
//        Event newEvent = eventService.createEvent(e);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Pre
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
