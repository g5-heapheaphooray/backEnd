package com.example.demo.dto;

import java.util.List;

import com.example.demo.dto.models.CleanRewardsDTO;

public class RewardsListDTO {
    public List<CleanRewardsDTO> rewards;

    public RewardsListDTO(List<CleanRewardsDTO> rewards) {
        this.rewards = rewards;
    }

    public List<CleanRewardsDTO> getRewards() {
        return rewards;
    }

    public void setRewards(List<CleanRewardsDTO> rewards) {
        this.rewards = rewards;
    }
}
