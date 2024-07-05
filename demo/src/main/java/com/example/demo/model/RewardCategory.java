package com.example.demo.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reward_cat")
public class RewardCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "points_needed")
    private int pointsNeeded;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "count")
    private int count;

    @Column(name = "next_available_index")
    private int nextAvailableIndex;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rewardCategory")
    private Set<RewardBarcode> rewards;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private RewardMedia rewardMedia;

    public RewardCategory(){
    }

    public RewardCategory(String name, int pointsNeeded, String type, String description, int count){
        this.name = name;
        this.pointsNeeded = pointsNeeded;
        this.type = type;
        this.description = description;
        this.count = count;
        this.rewards = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPointsNeeded() {
        return pointsNeeded;
    }

    public void setPointsNeeded(int pointsNeeded) {
        this.pointsNeeded = pointsNeeded;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<RewardBarcode> getRewards() {
        return rewards;
    }

    public void setRewards(Set<RewardBarcode> rewards) {
        this.rewards = rewards;
    }

    public int getNextAvailableIndex() {
        return nextAvailableIndex;
    }

    public void setNextAvailableIndex(int nextAvailableIndex) {
        this.nextAvailableIndex = nextAvailableIndex;
    }

    public RewardMedia getRewardMedia() {
        return rewardMedia;
    }

    public void setRewardMedia(RewardMedia rewardMedia) {
        this.rewardMedia = rewardMedia;
    }
}
