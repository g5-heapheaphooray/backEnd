package com.example.demo.dto;

import java.util.List;

import com.example.demo.dto.models.CleanRewardsCategoryDTO;

public class RewardsListDTO {
    public List<CleanRewardsCategoryDTO> rewards;

    public RewardsListDTO(List<CleanRewardsCategoryDTO> rewards) {
        this.rewards = rewards;
    }

    public List<CleanRewardsCategoryDTO> getRewards() {
        return rewards;
    }

    public void setRewards(List<CleanRewardsCategoryDTO> rewards) {
        this.rewards = rewards;
    }
}
