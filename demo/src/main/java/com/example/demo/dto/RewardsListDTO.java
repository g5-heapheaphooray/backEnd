package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Reward;

public class RewardsListDTO {
    public List<Reward> rewards;

    public RewardsListDTO(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }
}
