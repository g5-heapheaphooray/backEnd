package com.example.demo.repository;

import com.example.demo.model.RewardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardCategoryRepository extends JpaRepository<RewardCategory, Integer> {
    
}
