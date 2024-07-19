package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.dto.models.UserResponseDTO;
import com.example.demo.model.Admin;
import com.example.demo.model.Organisation;
import com.example.demo.service.AdminService;
import com.example.demo.service.OrganisationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.model.User;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final OrganisationService organisationService;

    @Autowired
    public AdminController(AdminService adminService, OrganisationService organisationService) {
        this.adminService = adminService;
        this.organisationService = organisationService;
    }

    @GetMapping("/verify/{orgId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> verifyOrg(@PathVariable String orgId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user instanceof Admin) {
            Organisation o = organisationService.updateVerified(orgId);
            if (o != null) {
                return new ResponseEntity<>("operation successful", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("operation unsuccessful", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all-volunteers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserListDTO> getAllVolunteers() {
        List<UserResponseDTO> cleanVols = adminService.getAllVol();
        UserListDTO res = new UserListDTO(cleanVols);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/get-volunteer/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CleanVolunteerDTO> getVolunteer(@PathVariable String email) {
        CleanVolunteerDTO res = adminService.getVol(email);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/all-organisation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserListDTO> getAllOrganisations() {
        List<UserResponseDTO> cleanOrgs = adminService.getAllOrg();
        UserListDTO res = new UserListDTO(cleanOrgs);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/blacklist/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> blacklistUser(@PathVariable String id) {
        User u = adminService.blacklistUser(id);
        if (u != null) {
            return new ResponseEntity<>("operation successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("operation unsuccessful", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/whitelist/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> whitelistUser(@PathVariable String id) {
        User u = adminService.whitelistUser(id);
        if (u != null) {
            return new ResponseEntity<>("operation successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("operation unsuccessful", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        User u = adminService.deleteUser(id);
        if (u != null) {
            return new ResponseEntity<>("operation successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("operation unsuccessful", HttpStatus.BAD_REQUEST);
    }


}
