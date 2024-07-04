package com.example.demo.dto;

import com.example.demo.dto.models.CleanRewardsBarcodeDTO;
import com.example.demo.dto.models.CleanRewardsDTO;

import java.util.List;

public class RewardBarcodesListDTO {
    public List<CleanRewardsBarcodeDTO> rewards;

    public RewardBarcodesListDTO(List<CleanRewardsBarcodeDTO> rewards) {
        this.rewards = rewards;
    }

    public List<CleanRewardsBarcodeDTO> getRewards() {
        return rewards;
    }

    public void setRewards(List<CleanRewardsBarcodeDTO> rewards) {
        this.rewards = rewards;
    }
}
