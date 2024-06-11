package com.example.demo.service;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import static com.example.demo.model.User.bytesToHex;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public UserService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public User createUser(User user) {
//        String plaintextpw = user.getPassword();
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
//        String encodedPassword = bCryptPasswordEncoder.encode(plainPassword);
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findById(email).orElse(null);
        if (user != null) {
            String pw = user.getPassword();
            String hash = null;
            try {
                final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
                final byte[] hashbytes = digest.digest(
                        password.getBytes(StandardCharsets.UTF_8));
                hash = bytesToHex(hashbytes);
            } catch (Exception e) {

            }
            if (pw.equals(hash)) {
                return user;
            }
        }

        return null;
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

    public boolean registerEvent(String event_id, String user_id) {
        Event event = eventRepository.findById(event_id).orElse(null);
        User user = userRepository.findById(user_id).orElse(null);
        if (event != null && user != null) {
            return event.addParticipant(user);
        }
        return false;
    }

}
