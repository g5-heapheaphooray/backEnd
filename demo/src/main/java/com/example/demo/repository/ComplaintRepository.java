package com.example.demo.repository;

import com.example.demo.model.Complaint;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer>{
    void deleteByUser(User user);
    void deleteByComplainee(User complainee);
}
