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
    public ResponseEntity<ResponseDTO> createOrganisation(@RequestBody RegisterOrganisationDTO o) {
        User newUser = userService.createOrganisation(o);
        ResponseDTO res = null;
        if (newUser == null) {
            res = new ResponseDTO("registration failed", 400);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {
            res = new ResponseDTO("registration success", 200);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<ResponseDTO> createAdmin(@RequestBody RegisterAdminDTO a) {
        User newUser = userService.createAdmin(a);
        ResponseDTO res = null;
        if (newUser == null) {
            res = new ResponseDTO("registration failed", 400);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {
            res = new ResponseDTO("registration success", 200);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
        User user = userService.authenticateUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
        ResponseDTO response = null;
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
            response = new JwtDTO("login success", 200, jwtToken, jwtService.getExpirationTime(), userType);
        } else {
            response = new ResponseDTO("login failure", 400);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<User> forgetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        User user = userService.updatePassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getOldPassword(), resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }




}
