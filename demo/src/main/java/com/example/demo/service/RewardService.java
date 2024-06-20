package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateRewardDTO;
import com.example.demo.model.Reward;
import com.example.demo.repository.RewardRepository;

@Service
public class RewardService {
    
    private final RewardRepository rewardRepository;

    @Autowired
    public RewardService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public Reward createReward(CreateRewardDTO r) {
        Reward newReward = new Reward(r.getName(), r.getPointsNeeded(), r.getBarcodeSerialNo(), r.getType(), r.getDescription());
        return rewardRepository.save(newReward);
    }

    public List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }

    public Reward getReward(int rewardId) {
        return rewardRepository.findById(rewardId).orElse(null);
    }

    public Reward updateReward(CreateRewardDTO r, int rewardId) {
        Reward currentReward = rewardRepository.findById(rewardId).orElse(null);

        if (currentReward == null) {
            return null;
        }

        currentReward.setName(r.getName());
        currentReward.setPointsNeeded(r.getPointsNeeded());
        currentReward.setBarcodeSerialNo(r.getBarcodeSerialNo());
        currentReward.setType(r.getType());
        currentReward.setDescription(r.getDescription());

        return rewardRepository.save(currentReward);
    }

    public Reward deleteReward(int rewardId) {
        Reward reward = getReward(rewardId);
        if (reward == null) {
            return null;
        }

        rewardRepository.delete(reward);
        return reward;
    }
}