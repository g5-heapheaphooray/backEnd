package com.example.demo.controller;

import com.example.demo.dto.AuthenticationDTO;
import com.example.demo.dto.MessageResponse;
import com.example.demo.dto.ResetPasswordDTO;
import com.example.demo.dto.UserResponse;
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
    public ResponseEntity<MessageResponse> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
//        System.out.println(payload);
        User user = userService.authenticateUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
        MessageResponse response = null;
        if (user != null) {
            char userType = 'A';
            if (user instanceof Organization) {
                userType = 'O';
            } else if (user instanceof Volunteer) {
                userType = 'V';
            }
            response = new UserResponse("login success", 200, user, userType);
        } else {
            response = new MessageResponse("login failure", 400);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<User> changePassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        User user = userService.updatePassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getOldPassword(), resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<User> forgetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        User user = userService.updatePassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getOldPassword(), resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestBody AuthenticationDTO authenticationDTO) {
        User user = userService.deleteUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
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
