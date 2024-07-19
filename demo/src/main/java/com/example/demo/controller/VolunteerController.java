package com.example.demo.controller;

import com.example.demo.dto.EventsListDTO;
import com.example.demo.dto.RegisterVolunteerDTO;
import com.example.demo.dto.models.CleanEventDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.model.*;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.VolunteerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;
    private final EventService eventService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService, EventService eventService) {
        this.volunteerService = volunteerService;
        this.eventService = eventService;
    }

    @GetMapping("/registeredEvents")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<EventsListDTO> getRegisteredEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Event> events = volunteerService.getRegisteredEvents(user.getEmail());
        List<CleanEventDTO> cleanEvents = new ArrayList<>();
        for (Event e : events) {
            cleanEvents.add(eventService.getCleanEvent(e));
        }
        EventsListDTO res = new EventsListDTO(cleanEvents);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/updateDetails")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<CleanVolunteerDTO> updateVolunteer(@RequestBody RegisterVolunteerDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Volunteer v = volunteerService.updateDetails(dto, user);
        CleanVolunteerDTO cv = volunteerService.getCleanVol(v);
        return new ResponseEntity<>(cv , HttpStatus.OK);
    }

    @PostMapping("/register/event/{eventId}")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<String> registerEvent(@PathVariable int eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user instanceof Volunteer) {
            try {
                volunteerService.registerEvent(eventId, (Volunteer) user);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("user has already registered for event", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>("event registration sucessful", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("event registration unsucessful", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unregister/event/{eventId}")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<String> unregisterEvent(@PathVariable int eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user instanceof Volunteer) {
            //update volunteer's event list
            volunteerService.unregisterEvent(eventId, user.getEmail());
            return new ResponseEntity<>("event unregistration sucessful", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("event unregistration unsucessful", HttpStatus.BAD_REQUEST);
    }
}
