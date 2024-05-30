package com.example.demo.controller;

import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/api/v1/user")
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-volunteer")
    public ResponseEntity<User> createVolunteer(@RequestBody Volunteer v) {
        User newUser = userService.createUser(v);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/register-organisation")
    public ResponseEntity<User> createOrganisation(@RequestBody Organization o) {
        User newUser = userService.createUser(o);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody Map<String, String> payload) {
        System.out.println(payload);
        User user = userService.loginUser(payload.get("email"), payload.get("password"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<User> changePassword(@RequestBody Map<String, String> payload) {
        User user = userService.updatePassword(payload.get("email"), payload.get("currentPassword"), payload.get("newPassword"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<User> forgetPassword(@RequestBody Map<String, String> payload) {
        User user = userService.updatePassword(payload.get("email"), payload.get("currentPassword"), payload.get("newPassword"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestBody Map<String, String> payload) {
        User user = userService.deleteUser(payload.get("email"), payload.get("password"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


//    @GetMapping("/find/{email}")
//    public ResponseEntity<User> getUser(@PathVariable String email) {
//        User user = userService.getUser(email);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }



//    @PutMapping("/updateTest")
//    public ResponseEntity<User> updateUser(@RequestBody Map<String, String> payload) {
//        User user = userService.updateHours(payload.get("email"), Double.parseDouble(payload.get("hours")));
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }


    

}
