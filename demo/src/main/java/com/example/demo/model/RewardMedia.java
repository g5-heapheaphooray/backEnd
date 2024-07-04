package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reward_media")
public class RewardMedia extends Media {
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "media")
    private RewardCategory rewardCategory;

    public RewardMedia() {
    }

    public RewardMedia(String filename, String filepath, RewardCategory rewardCategory) {
        super(filename, filepath);
        this.rewardCategory = rewardCategory;
    }

    public RewardCategory getRewardCategory() {
        return rewardCategory;
    }

    public void setRewardCategory(RewardCategory rewardCategory) {
        this.rewardCategory = rewardCategory;
    }
}
