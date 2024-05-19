package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Reward;
import com.example.demo.repository.RewardRepository;

@Service
public class RewardService {
    
    private final RewardRepository rewardRepository;

    @Autowired
    public RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public Reward createReward(Reward reward) {
        return rewardRepository.save(reward);
    }
}