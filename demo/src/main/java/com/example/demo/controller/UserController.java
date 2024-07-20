package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Organisation;
import com.example.demo.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.model.User;
import com.example.demo.service.UserService;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userid}")
    public ResponseEntity<Object> getUser(@PathVariable String userid) {
        User user = userService.getUser(userid);
        Object res = null;
        if (user instanceof Volunteer v) {
            res = userService.getCleanVolunteer(v);
        } else if (user instanceof Organisation o) {
            res = userService.getCleanOrganisation(o);

        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user != null) {
            Object res = null;
            if (user instanceof Volunteer v) {
                res = userService.getCleanVolunteer(v);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else if (user instanceof Organisation o) {
                res = userService.getCleanOrganisation(o);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
            }
        }       
        return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        User u = userService.updatePassword(user, changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        Object res = null;
        if (u instanceof Volunteer v) {
            res = userService.getCleanVolunteer(v);
        } else if (u instanceof Organisation o) {
            res = userService.getCleanOrganisation(o);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('VOLUNTEER', 'ORGANISATION')")
    public ResponseEntity<Object> deleteUser(@RequestBody AuthenticationDTO authenticationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuth = (User) authentication.getPrincipal();
        User user = userService.deleteUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
        if (userAuth == null || user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Object res = null;
        if (user instanceof Volunteer v) {
            res = userService.getCleanVolunteer(v);
        } else if (user instanceof Organisation o) {
            res = userService.getCleanOrganisation(o);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
