package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Reward;
import com.example.demo.service.RewardService;

@RestController
@RequestMapping("/api/v1/reward")
public class RewardController {
    
    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/create")
    public ResponseEntity<Reward> createReward(@RequestBody Reward reward) {
        Reward newReward = rewardService.createReward(reward);
        return new ResponseEntity<>(newReward, HttpStatus.CREATED);
    }
}
