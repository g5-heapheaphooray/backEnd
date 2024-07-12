package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.dto.RegisterAdminDTO;
import com.example.demo.dto.RegisterVolunteerDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
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

    public User blacklistUser(String id) {
        User u = this.userRepository.findById(id).orElse(null);
        if (u == null) {
            return null;
        }
        u.setLocked(true);
        return userRepository.save(u);
    }

    public User whitelistUser(String id) {
        User u = this.userRepository.findById(id).orElse(null);
        if (u == null) {
            return null;
        }
        u.setLocked(false);
        return userRepository.save(u);
    }


    public User deleteUser(String id){
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        userRepository.delete(user);
        return user;
    }

    public UserResponseDTO getCleanOrg(Organisation o) {
        byte[] pfpBytes = mediaService.getMedia(o.getPfp().getFilepath());
        return new UserResponseDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(),
                o.isVerified(), !o.isAccountNonLocked(), 'O', pfpBytes);
    }

    public UserResponseDTO getCleanVol(Volunteer v) {
        byte[] pfpBytes = mediaService.getMedia(v.getPfp().getFilepath());
        return new UserResponseDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(), v.getGender(), v.getDob(), v.getHours(), v.getPoints(),
                'V', !o.isAccountNonLocked(), pfpBytes);
    }
    public List<UserResponseDTO> getAllOrg() {
        List<Organisation> orgs = organisationRepository.findAll();
        List<UserResponseDTO> cleanOrgs = new ArrayList<>();
        for (Organisation o : orgs) {
            cleanOrgs.add(getCleanOrg(o));
        }
        return cleanOrgs;
    }

    public List<UserResponseDTO> getAllVol() {
        List<Volunteer> vols = volunteerRepository.findAll();
        List<UserResponseDTO> cleanVols = new ArrayList<>();
        for (Volunteer v : vols) {
            cleanVols.add(getCleanVol(v));
        }
        return cleanVols;
    }



}