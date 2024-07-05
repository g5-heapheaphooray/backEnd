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
        User user = userService.authenticateUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
        System.out.println(user);
        if (user != null) {
            String jwtToken = jwtService.generateToken(user);
            System.out.println(jwtToken);
            char userType = 'A';
            if (user instanceof Organisation) {
                userType = 'O';
            } else if (user instanceof Volunteer) {
                userType = 'V';
            }
//            response = new UserResponse("login success", 200, user, userType)
            return new ResponseEntity<>(new JwtDTO("login success", jwtToken, jwtService.getExpirationTime(), userType), HttpStatus.OK);
        } 
        return new ResponseEntity<>("login failure", HttpStatus.OK);
    }

//    @PostMapping("/forget-password")
//    public ResponseEntity<User> forgetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
//        User user = userService.updatePassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getOldPassword(), resetPasswordDTO.getNewPassword());
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @GetMapping("/forget-password/{email}")
    public ResponseEntity<String> forgetPassword(@PathVariable String email) {
        System.out.println(email);
        String res = userService.forgetPassword(email);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        String res = userService.resetPassword(token, resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
