package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.VolunteerService;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

//    @GetMapping("/find/{email}")
//    public ResponseEntity<User> getUser(@PathVariable String email) {
//        User user = userService.getUser(email);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

//    @PutMapping("/updateHours/{id}")
//    public ResponseEntity<Volunteer> updateHours(@PathVariable String id, @RequestBody double hours){
//        User updatedUser = volunteerService.updateHours(id, hours);
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }

    @PutMapping("/updateDetails")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<Volunteer> updateVolunteer(@RequestBody Map<String, String> payload) {
        Volunteer v = volunteerService.updateDetails(payload.get("email"), payload);
        return new ResponseEntity<>(v , HttpStatus.OK);
    }

    @PostMapping("/register/event/{eventId}")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<ResponseDTO> registerEvent(@PathVariable String eventId, @RequestBody String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
//        System.out.println(payload.get("userId"));
        ResponseDTO res = new ResponseDTO("event registration unsucessful", 400);
        if (user instanceof Volunteer) {
            //update volunteer's event list
            volunteerService.updateEventsParticipated(eventId, userId);
            res = new ResponseDTO("event registration sucessful", 200);
        }

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
