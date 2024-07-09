package com.example.demo.controller;

import com.example.demo.dto.CreateOppDTO;
import com.example.demo.dto.EventsListDTO;
import com.example.demo.dto.RegisterForEventDTO;
import com.example.demo.dto.VolListDTO;
import com.example.demo.dto.models.CleanEventDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.model.Organisation;
import com.example.demo.model.User;
import com.example.demo.model.Volunteer;
import com.example.demo.service.OrganisationService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/event")
public class EventController {
    
    private final EventService eventService;
    private final OrganisationService organisationService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, OrganisationService organisationService, UserService userService) {
        this.eventService = eventService;
        this.organisationService = organisationService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<CleanEventDTO> createEvent(@RequestBody CreateOppDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getFullName());
        System.out.println("name" + dto.getName());
        if (user instanceof Organisation) {
            Organisation o = (Organisation) user;
            Event e = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), o, dto.getManpowerCount(),
                    dto.getLocation(), dto.getDescription(), dto.getType(), dto.getAddress(), dto.getSkills(), dto.getCauses(), null);
            System.out.println(e.getName());
            Event newEvent = eventService.createEvent(e, user);
            if (newEvent != null) {
                CleanEventDTO ce = eventService.getCleanEvent(newEvent);
                return new ResponseEntity<>(ce, HttpStatus.CREATED);
            }
        }
//        System.out.println(dto.getOrganisationEmail());
//        System.out.println(dto);
//        Organisation o = organisationService.getOrg(dto.getOrganisationEmail());
//        Event e = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), o, dto.getManpowerCount(), dto.getLocation(), dto.getDescription(), dto.getType());
//        Event newEvent = eventService.createEvent(e);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/all")
    public ResponseEntity<EventsListDTO> allEvents() {
        List<CleanEventDTO> cleanEvents = eventService.getAllEvents();
        EventsListDTO res = new EventsListDTO(cleanEvents);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{orgId}")
    public ResponseEntity<EventsListDTO> orgEvents(@PathVariable String orgId) {
        Organisation o = organisationService.getOrg(orgId);
        List<CleanEventDTO> cleanEvents = eventService.getOrgEvents(o);
        EventsListDTO res = new EventsListDTO(cleanEvents);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/get/{eventId}")
    @PreAuthorize("hasAnyRole('ORGANISATION', 'VOLUNTEER')")
    public ResponseEntity<CleanEventDTO> getEvent(@PathVariable String eventId) {
        Event event = eventService.getEvent(eventId);
        if (event == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        CleanEventDTO res = eventService.getCleanEvent(event);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/update/{eventId}")
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<String> updateEvent(@PathVariable String eventId, @RequestBody CreateOppDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user instanceof Organisation) {
            // Event event = new Event(dto.getName(), dto.getDate(), dto.getStartTime(), dto.getEndTime(), user.getEmail(), dto.getManpowerCount(), dto.getLocation(), dto.getDescription(), dto.getType());
            eventService.updateEvent(dto, eventId);
            return new ResponseEntity<>("event update sucessful", HttpStatus.OK);
        }

        return new ResponseEntity<>("event update unsucessful", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{eventId}")
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<String> deleteEvent(@PathVariable String eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user instanceof Organisation) {
            eventService.deleteEvent(eventId);
            return new ResponseEntity<>("event deletion sucessful", HttpStatus.OK);
        }
        return new ResponseEntity<>("event deletion unsucessful", HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/{eventId}/participants")
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<VolListDTO> eventPartipants(@PathVariable String eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Event e = eventService.getEvent(eventId);   
        if (e == null || !e.getOrganisation().getEmail().equals(user.getEmail())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<CleanVolunteerDTO> participants = eventService.getEventParticipants(eventId);
        VolListDTO res = new VolListDTO(participants);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // @GetMapping("/{eventId}/participants/attendance")
    // @PreAuthorize("hasRole('ORGANISATION')")
    // public ResponseEntity<VolListDTO> getEventAttendance(@PathVariable String eventId) {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     User user = (User) authentication.getPrincipal();
    //     Event e = eventService.getEvent(eventId);
    //     if (e == null || !e.getOrganisation().getEmail().equals(user.getEmail())) {
    //         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    //     }
    //     List<CleanVolunteerDTO> participants = eventService.getEventParticipants(eventId);
    //     VolListDTO res = new VolListDTO(participants);
    //     return new ResponseEntity<>(res, HttpStatus.OK);
    // }

    @PostMapping("/{eventId}/participants/attendance")
    @PreAuthorize("hasRole('ORGANISATION')")
    public ResponseEntity<VolListDTO> setEventAttendance(@PathVariable String eventId, @RequestBody List<CleanVolunteerDTO> dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Event e = eventService.getEvent(eventId);
        if (e == null || !e.getOrganisation().getEmail().equals(user.getEmail())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        eventService.setEventParticipants(eventId, dto);
        VolListDTO res = new VolListDTO(dto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
