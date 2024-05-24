package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findById(email).orElse(null);

        return user;
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
        updatedUser.setFullName(fullName);
        updatedUser.setContactNo(contactNo);
        return userRepository.save(updatedUser);

    }

}
