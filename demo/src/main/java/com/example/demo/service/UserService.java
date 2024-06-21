package com.example.demo.service;

import com.example.demo.dto.RegisterAdminDTO;
import com.example.demo.dto.RegisterOrganisationDTO;
import com.example.demo.dto.RegisterVolunteerDTO;
import com.example.demo.model.*;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import static com.example.demo.model.User.bytesToHex;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserService(UserRepository userRepository, EventRepository eventRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
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
        userRepository.delete(user);
        return user;
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
