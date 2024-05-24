package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Event;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findById(email).orElse(null);
        if (user != null) {
            String pw = user.getPassword();
            if (pw.equals(password)) {
                return user;
            }
        }

        return null;
    }

    public User getUser(String email) {
        return userRepository.findById(email).orElse(null);

    }

    public User updateContactNo(String id, String contactNo){
        User updatedUser = userRepository.findById(id).orElse(null);
        if(updatedUser == null) {
            return null;
        }

        updatedUser.setContactNo(contactNo);
        return userRepository.save(updatedUser);

    }

    public User updatePassword(String id, String password){
        User updatedUser = userRepository.findById(id).orElse(null);
        if(updatedUser == null){
            return null;
        }

        updatedUser.setPassword(password);
        return userRepository.save(updatedUser);

    }

    public User updateHours(String id, int hours){
        User updatedUser = userRepository.findById(id).orElse(null);
        if(updatedUser == null){
            return null;
        }
        updatedUser.setHours(updatedUser.getHours() + hours);
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

    public User updatePoints(String id, int points){
        User updatedUser = userRepository.findById(id).orElse(null);
        if(updatedUser == null){
            return null;
        }
        updatedUser.setPoints(updatedUser.getPoints() + points);
        return userRepository.save(updatedUser);

    }

    public User updateEventsPart(String id, Event event){
        User updatedUser = userRepository.findById(id).orElse(null);
        if(updatedUser == null){
            return null;
        }
        updatedUser.getEventsPart().add(event);
        return userRepository.save(updatedUser);

    }

    public User deleteUser(String id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return user.get();
        }

        return null;
    }

}
