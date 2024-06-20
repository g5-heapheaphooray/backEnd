package com.example.demo.controller;

import com.example.demo.dto.CreateOppDTO;
import com.example.demo.dto.EventsListDTO;
import com.example.demo.dto.RegisterForEventDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.Organization;
import com.example.demo.model.User;
import com.example.demo.model.Volunteer;
import com.example.demo.service.OrganizationService;
import com.example.demo.service.UserService;

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
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, OrganizationService organizationService, UserService userService) {
        this.eventService = eventService;
        this.organizationService = organizationService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<ResponseDTO> createEvent(@RequestBody CreateOppDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getFullName());
        System.out.println("name" + dto.getName());
        ResponseDTO res = new ResponseDTO("event creation unsucessful", 400);
        if (user instanceof Organization) {
//            Organization o = (Organization) user;
            Event e = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), user.getEmail(), dto.getManpowerCount(), dto.getLocation(), dto.getDescription(), dto.getType());
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

    @GetMapping("/get/{eventId}")
    @PreAuthorize("hasAnyRole('ORGANIZATION', 'VOLUNTEER')")
    public ResponseEntity<Event> getEvent(@PathVariable String eventId) {
        Event event = eventService.getEvent(eventId);
        if (event == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping("/update/{eventId}")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<ResponseDTO> updateEvent(@PathVariable String eventId, @RequestBody CreateOppDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("event update unsucessful", 400);
        if (user instanceof Organization) {
            // Event event = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), user.getEmail(), dto.getManpowerCount(), dto.getLocation(), dto.getDescription(), dto.getType());
            eventService.updateEvent(dto, eventId);
            res = new ResponseDTO("event update sucessful", 200);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{eventId}")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<ResponseDTO> deleteEvent(@PathVariable String eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("event deletion unsucessful", 400);
        if (user instanceof Organization) {
            eventService.deleteEvent(eventId);
            res = new ResponseDTO("event deletion sucessful", 200);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
