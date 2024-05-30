package com.example.demo.controller;

import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Volunteer;
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
    public ResponseEntity<Volunteer> updateVolunteer(@RequestBody Map<String, String> payload) {
        Volunteer v = volunteerService.updateDetails(payload.get("email"), payload);
        return new ResponseEntity<>(v , HttpStatus.OK);
    }


}
