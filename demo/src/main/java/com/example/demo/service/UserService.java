package com.example.demo.service;

import com.example.demo.dto.RegisterAdminDTO;
import com.example.demo.dto.RegisterOrganisationDTO;
import com.example.demo.dto.RegisterVolunteerDTO;
import com.example.demo.dto.models.CleanOrganisationDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.model.*;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VolunteerRepository;
import com.example.demo.service.EventService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final EventService eventService;
    private final VolunteerRepository volunteerRepository;
    private final MailService mailService;
    private final MediaService mediaService;

    @Value("${frontend.source}")
    private String frontendSource;

    @Autowired
    public UserService(UserRepository userRepository, EventRepository eventRepository, VolunteerRepository volunteerRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, EventService eventService, MailService mailService, MediaService mediaService) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.volunteerRepository = volunteerRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.eventService = eventService;
        this.mailService = mailService;
        this.mediaService = mediaService;
    }

    public CleanVolunteerDTO getCleanVolunteer(Volunteer v) {
        return new CleanVolunteerDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(), v.getGender(), v.getDob(), v.getHours(), v.getPoints(), mediaService.getObjectUrl(v.getPfp().getFilepath()));
    }

    public CleanOrganisationDTO getCleanOrganisation(Organisation o) {
        return new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), mediaService.getObjectUrl(o.getPfp().getFilepath()));
    }

    public User createVolunteer(RegisterVolunteerDTO v) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.VOLUNTEER);
        if (optionalRole.isEmpty()) {
            return null;
        }

        User u = new Volunteer(v.getFullName(), v.getGender(), v.getDob(), v.getEmail(), v.getContactNo(), v.getPassword(), optionalRole.get());
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    public User createOrganisation(RegisterOrganisationDTO o) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ORGANISATION);
        if (optionalRole.isEmpty()) {
            return null;
        }

        User u = new Organisation(o.getEmail(), o.getFullName(), o.getPassword(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), optionalRole.get());
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    public User createVerifiedOrganisation(RegisterOrganisationDTO o) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ORGANISATION);
        if (optionalRole.isEmpty()) {
            return null;
        }

        User u = new Organisation(o.getEmail(), o.getFullName(), o.getPassword(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), optionalRole.get());
        Organisation org = (Organisation) u;
        org.setVerified(true);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    public User createAdmin(RegisterAdminDTO a) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);
        if (optionalRole.isEmpty()) {
            return null;
        }

        User u = new Admin(a.getFullName(), a.getEmail(), a.getContactNo(), a.getPassword(), optionalRole.get());
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    public User authenticateUser(String email, String password) {
        System.out.println(email);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return userRepository.findById(email).orElse(null);
//        User user = userRepository.findById(email).orElse(null);
//        if (user != null) {
//            String pw = user.getPassword();
//            String hash = null;
//            try {
//                final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
//                final byte[] hashbytes = digest.digest(
//                        password.getBytes(StandardCharsets.UTF_8));
//                hash = bytesToHex(hashbytes);
//            } catch (Exception e) {
//
//            }
//            if (pw.equals(hash)) {
//                return user;
//            }
//        }
//
//        return null;
    }

    public User getUser(String email) {
        return userRepository.findById(email).orElse(null);

    }

    public User updatePassword(String id, String currentPassword, String newPassword){
        User updatedUser = userRepository.findById(id).orElse(null);
        if(updatedUser == null){
            return null;
        }
        User verifiedUser = authenticateUser(id, currentPassword);
        if (verifiedUser == null) {
            return null;
        }
        updatedUser.setPassword(newPassword);
        return userRepository.save(updatedUser);

    }

    public User updateComplainCount(String id, int complainCount){
        User updatedUser = userRepository.findById(id).orElse(null);
        if(updatedUser == null){
            return null;
        }
        updatedUser.setComplainCount(updatedUser.getComplainCount() + complainCount);
        return userRepository.save(updatedUser);

    }

    public User deleteUser(String id, String password){
        User user = authenticateUser(id, password);
        if (user == null) {
            return null;
        }

        if (user instanceof Volunteer) {
            // delete all events_part
            // eventRepository.deleteParticipant(id);
            for (Event event : user.getEventsPart()) {
                event.getParticipants().remove(user); // Assuming `getParticipants` returns the list of users participating in the event
                eventRepository.save(event); // Save the event entity after removing the user
            }
        } 
        userRepository.delete(user);
        return user;
    }

    public String forgetPassword(String email) {
        User user = userRepository.findById(email).orElse(null);
        if (user == null) {
            return "User not found";
        }
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        userRepository.save(user);
        String content = "http://" + frontendSource + "/reset-password/"+token;
        mailService.sendMail(email, "Reset Password", content);
        return "Email sent";
    }

    public String resetPassword(String token, String newPassword) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            return "User not found";
        } else if (!user.tokenStillValid()) {
            return "Expired token";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerificationToken(null);
        userRepository.save(user);
        return "Password changed";
    }


    // public boolean registerEvent(String eventid, String userid) {
    //     Event event = eventRepository.findById(eventid).orElse(null);
    //     User user = userRepository.findById(userid).orElse(null);
    //     if (event != null && user != null) {
    //         return event.addParticipant(user);
    //     }
    //     return false;
    // }

    // public User updateEventsParticipated(String eventid, String userid) {
    //     Event event = eventRepository.findById(eventid).orElse(null);
    //     User user = userRepository.findById(userid).orElse(null);
    //     if (event == null || user == null) {
    //         return null;
    //     }

    //     Set<Event> eventList = user.getEventsPart();
    //     eventList.add(event);
    //     user.setEventsPart(eventList);
    //     return userRepository.save(user);
    // }

}
