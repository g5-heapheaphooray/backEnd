package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.*;

@RestController
@CrossOrigin
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
    public ResponseEntity<String> createVolunteer(@RequestBody RegisterVolunteerDTO v) {
        User newUser = userService.createVolunteer(v);
        if (newUser != null) {
            return new ResponseEntity<>("registration successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("registration unsuccessful", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register-organisation")
    public ResponseEntity<String> createOrganisation(@RequestBody RegisterOrganisationDTO o) {
        User newUser = userService.createOrganisation(o);
        if (newUser != null) {
            return new ResponseEntity<>("registration successful", HttpStatus.CREATED);
        } 
        return new ResponseEntity<>("registration unsuccessful", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> createAdmin(@RequestBody RegisterAdminDTO a) {
        User newUser = userService.createAdmin(a);
        if (newUser != null) {
            return new ResponseEntity<>("registration successful", HttpStatus.CREATED);
        } 
        return new ResponseEntity<>("registration unsuccessful", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
        System.out.println("hello new new new");
        System.out.println("wruu");
        System.out.println("helppppp");
        System.out.println("pls i beg u");
        System.out.println("kms");
        User user = userService.authenticateUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
        if (user != null) {
            String jwtToken = jwtService.generateToken(user);
            char userType = 'A';
            if (user instanceof Organisation) {
                userType = 'O';
            } else if (user instanceof Volunteer) {
                userType = 'V';
            }
            return new ResponseEntity<>(new JwtDTO("login success", jwtToken, jwtService.getExpirationTime(), userType), HttpStatus.OK);
        } 
        return new ResponseEntity<>("login failure", HttpStatus.OK);
    }

    @GetMapping("/forget-password/{email}")
    public ResponseEntity<String> forgetPassword(@PathVariable String email) {
        String res = userService.forgetPassword(email);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        String res = userService.resetPassword(token, resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
