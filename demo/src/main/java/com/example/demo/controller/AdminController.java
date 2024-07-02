package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.models.CleanOrganisationDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.model.Admin;
import com.example.demo.model.Organisation;
import com.example.demo.model.Volunteer;
import com.example.demo.service.AdminService;
import com.example.demo.service.OrganisationService;

import java.util.ArrayList;
import java.util.List;

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
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all-volunteers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VolListDTO> getAllVolunteers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Volunteer> volunteers = adminService.getAllVolunteers();
        List<CleanVolunteerDTO> cleanVols = new ArrayList<>();
        for (Volunteer v : volunteers) {
            CleanVolunteerDTO cv = new CleanVolunteerDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(), v.getGender(), v.getDob(), v.getHours(), v.getPoints(), v.getPfp().getFilepath());
            cleanVols.add(cv);
        }
        VolListDTO res = new VolListDTO(cleanVols);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/all-organisation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrgListDTO> getAllOrganisations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Organisation> orgs = organisationService.getAllOrg();
        List<CleanOrganisationDTO> cleanOrgs = new ArrayList<>();
        for (Organisation o : orgs) {
            CleanOrganisationDTO co = new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), o.getPfp().getFilepath());
            cleanOrgs.add(co);
        }
        OrgListDTO res = new OrgListDTO(cleanOrgs);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/blacklist/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> blacklistUser(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("operation unsuccessful", 400);
        User u = adminService.blacklistUser(id);
        if (u != null) {
            res = new ResponseDTO("operation successful", 200);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/whitelist/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> whitelistUser(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("operation unsuccessful", 400);
        User u = adminService.whitelistUser(id);
        if (u != null) {
            res = new ResponseDTO("operation successful", 200);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("operation unsuccessful", 400);
        User u = adminService.deleteUser(id);
        if (u != null) {
            res = new ResponseDTO("operation successful", 200);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }


}
