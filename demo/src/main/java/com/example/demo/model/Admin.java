package com.example.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@DiscriminatorValue("A")
public class Admin extends User{
    public Admin() {

    }
    public Admin(String fullName, String email, String contactNo, String password, Role role){
        super(fullName, email, contactNo, password, new ArrayList<>(), null, role);
    }
}
