package com.example.demo.service;

import java.util.Map;
import java.util.Optional;

import com.example.demo.dto.RegisterAdminDTO;
import com.example.demo.dto.RegisterVolunteerDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final VolunteerRepository volunteerRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminService(OrganisationRepository organisationRepository, UserRepository userRepository, EventRepository eventRepository, VolunteerRepository volunteerRepository, RoleRepository roleRepository) {
        this.organisationRepository = organisationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.roleRepository = roleRepository;
    }

//    public Organisation createOrganisation(Organisation organisation) {
//        return organisationRepository.save(organisation);
//    }

    public Organisation updateVerified(String id) {
        Organisation o = organisationRepository.findById(id).orElse(null);
        if(o == null){
            return null;
        }
        o.setVerified(true);

        return organisationRepository.save(o);
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public User blacklistUser(String id) {
        User u = this.userRepository.findById(id).orElse(null);
        if (u == null) {
            return null;
        }
        u.setLocked(true);
        return userRepository.save(u);
    }



}