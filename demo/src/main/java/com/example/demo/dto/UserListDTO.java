package com.example.demo.dto;

import java.util.List;

import com.example.demo.dto.models.UserResponseDTO;
public class UserListDTO {
    public List<UserResponseDTO> users;

    public UserListDTO(List<UserResponseDTO> users) {
        this.users = users;
    }

    public List<UserResponseDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponseDTO> users) {
        this.users = users;
    }
}