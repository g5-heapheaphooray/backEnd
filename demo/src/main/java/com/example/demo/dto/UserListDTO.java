package com.example.demo.dto;

import java.util.List;

import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.model.Volunteer;

public class UserListDTO {
    public List<UserListDTO> users;

    public UserListDTO(List<UserListDTO> users) {
        this.users = users;
    }

    public List<UserListDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserListDTO> users) {
        this.users = users;
    }
}