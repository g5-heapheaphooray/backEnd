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

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final MediaService mediaService;

    @Value("${frontend.source}")
    private String frontendSource;

    @Autowired
    public UserService(UserRepository userRepository, EventRepository eventRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, MailService mailService, MediaService mediaService) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        return userRepository.findById(email).orElse(null);
    }

    public User getUser(String email) {
        return userRepository.findById(email).orElse(null);

    }

    public User updatePassword(User u, String currentPassword, String newPassword){
        if (u == null){
            return null;
        }
        User verifiedUser = authenticateUser(u.getEmail(), currentPassword);
        if (verifiedUser == null) {
            return null;
        }
        u.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(u);

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
}
