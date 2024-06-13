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

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final VolunteerRepository volunteerRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminService(OrganizationRepository organizationRepository, UserRepository userRepository, EventRepository eventRepository, VolunteerRepository volunteerRepository, RoleRepository roleRepository) {
        this.organizationRepository = organizationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.roleRepository = roleRepository;
    }

//    public Organization createOrganization(Organization organization) {
//        return organizationRepository.save(organization);
//    }

    public Organization updateVerified(String id) {
        Organization o = organizationRepository.findById(id).orElse(null);
        if(o == null){
            return null;
        }
        o.setVerified(true);

        return organizationRepository.save(o);
    }



}