package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.CreateRewardDTO;
import com.example.demo.dto.RewardsListDTO;
import com.example.demo.model.Reward;
import com.example.demo.service.RewardService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/reward")
public class RewardController {
    
    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reward> createReward(@RequestBody CreateRewardDTO r) {
        Reward newReward = rewardService.createReward(r);
        return new ResponseEntity<>(newReward, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RewardsListDTO> allRewards() {
        List<Reward> rewards = rewardService.getAllRewards();
        RewardsListDTO res = new RewardsListDTO(rewards);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/get/{rewardId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Reward> getReward(@PathVariable int rewardId) {
        Reward reward = rewardService.getReward(rewardId);
        return new ResponseEntity<>(reward, HttpStatus.OK);
    }

    @PutMapping("/update/{rewardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reward> updateReward(@PathVariable int rewardId, @RequestBody CreateRewardDTO r) {
        Reward updatedReward = rewardService.updateReward(r, rewardId);
        return new ResponseEntity<>(updatedReward, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{rewardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reward> deleteReward(@PathVariable int rewardId) {
        Reward deletedReward = rewardService.deleteReward(rewardId);
        return new ResponseEntity<>(deletedReward, HttpStatus.OK);
    }
}
