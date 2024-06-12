package com.example.demo.controller;
// routes that dont need authentication

import com.example.demo.dto.*;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register-volunteer")
    public ResponseEntity<ResponseDTO> createVolunteer(@RequestBody RegisterVolunteerDTO v) {
        User newUser = userService.createVolunteer(v);
        ResponseDTO res = null;
        if (newUser == null) {
            res = new ResponseDTO("registration failed", 400);
        } else {
            res = new ResponseDTO("registration success", 200);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/register-organisation")
    public ResponseEntity<User> createOrganisation(@RequestBody RegisterOrganisationDTO o) {
        User newUser = userService.createOrganisation(o);
        ResponseDTO res = null;
        if (newUser == null) {
            res = new ResponseDTO("registration failed", 400);
        } else {
            res = new ResponseDTO("registration success", 200);
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
        User user = userService.authenticateUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
        ResponseDTO response = null;
        if (user != null) {
            String jwtToken = jwtService.generateToken(user);
//            char userType = 'A';
//            if (user instanceof Organization) {
//                userType = 'O';
//            } else if (user instanceof Volunteer) {
//                userType = 'V';
//            }
//            response = new UserResponse("login success", 200, user, userType)
            response = new JwtDTO("login success", 200, jwtToken, jwtService.getExpirationTime());
        } else {
            response = new ResponseDTO("login failure", 400);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<User> forgetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        User user = userService.updatePassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getOldPassword(), resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }




}
