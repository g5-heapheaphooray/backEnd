package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Admin;
import com.example.demo.model.Organisation;
import com.example.demo.model.Volunteer;
import com.example.demo.service.AdminService;
import com.example.demo.service.OrganisationService;
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
//@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600, allowedHeaders = "*", methods = "*")
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
    public ResponseEntity<ResponseDTO> verifyOrg(@PathVariable String orgId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("operation unsuccessful", 400);
        if (user instanceof Admin) {
            Organisation o = organisationService.updateVerified(orgId);
            if (o != null) {
                res = new ResponseDTO("operation successful", 200);
            }
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
