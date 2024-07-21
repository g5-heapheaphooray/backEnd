package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.dto.models.UserResponseDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.service.*;

@Service
public class AdminService {

    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;
    private final VolunteerRepository volunteerRepository;
    private final MediaService mediaService;
    private final UserService userService;
    private final EventRepository eventRepository;
    private final ComplaintRepository complaintRepository;
    private final MailService mailService;

    @Autowired
    public AdminService(OrganisationRepository organisationRepository, UserRepository userRepository, VolunteerRepository volunteerRepository,
                        MediaService mediaService, UserService userService, EventRepository eventRepository, ComplaintRepository complaintRepository,  MailService mailService) {
        this.organisationRepository = organisationRepository;
        this.userRepository = userRepository;
        this.volunteerRepository = volunteerRepository;
        this.mediaService = mediaService;
        this.userService = userService;
        this.eventRepository = eventRepository;
        this.complaintRepository = complaintRepository;
        this.mailService = mailService;
    }

    public Organisation updateVerified(String id) {
        Organisation o = organisationRepository.findById(id).orElse(null);
        if(o == null){
            return null;
        }
        o.setVerified(true);
        String msg = "Your account has been verified! You may proceed to login at http://" + frontendSource + "/login";
        mailService.sendMail(o.getEmail(), "Account Verified", msg);
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

    @Transactional
    public User deleteUser(String id){
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }

        complaintRepository.deleteByUser(user);
        complaintRepository.deleteByComplainee(user);

        for (Event event : user.getEventsPart()) {
            event.getParticipants().remove(user);
            eventRepository.save(event);
        }

        userRepository.delete(user);
        return user;
    }

    public UserResponseDTO getCleanOrg(Organisation o) {
        return new UserResponseDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(),
                o.isVerified(), !o.isAccountNonLocked(), 'O', mediaService.getObjectUrl(o.getPfp().getFilepath()));
    }

    public UserResponseDTO getCleanVol(Volunteer v) {
        return new UserResponseDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(), v.getGender(), v.getDob(), v.getHours(), v.getPoints(),
                'V', !v.isAccountNonLocked(), mediaService.getObjectUrl(v.getPfp().getFilepath()));
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

    public CleanVolunteerDTO getVol(String email) {
        Volunteer v = volunteerRepository.findById(email).orElse(null);
        if (v == null) {
            return null;
        }
        return userService.getCleanVolunteer(v);
    }
}