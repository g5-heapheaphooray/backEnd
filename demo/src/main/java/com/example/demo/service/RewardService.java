package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.demo.model.RewardCategory;
import com.example.demo.model.RewardMedia;
import com.example.demo.model.Volunteer;
import com.example.demo.repository.MediaRepository;
import com.example.demo.repository.RewardCategoryRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateRewardDTO;
import com.example.demo.dto.models.CleanRewardsCategoryDTO;
import com.example.demo.model.RewardBarcode;
import com.example.demo.repository.RewardBarcodeRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RewardService {
    
    private final RewardBarcodeRepository rewardBarcodeRepository;
    private final RewardCategoryRepository rewardCategoryRepository;
    private final UserRepository userRepository;
    private final MediaService mediaService;

    @Autowired
    public RewardService(RewardBarcodeRepository rewardBarcodeRepository, RewardCategoryRepository rewardCategoryRepository, UserRepository userRepository, MediaService mediaService) {
        this.rewardBarcodeRepository = rewardBarcodeRepository;
        this.rewardCategoryRepository = rewardCategoryRepository;
        this.userRepository = userRepository;
        this.mediaService = mediaService;
    }

    public CleanRewardsCategoryDTO getCleanRewardCategory(RewardCategory rc) {
        return new CleanRewardsCategoryDTO(rc.getId(), rc.getName(), rc.getPointsNeeded(), rc.getType(), rc.getDescription(), rc.getCount(), rc.getRewardMedia().getFilepath());
    }

    public CleanRewardsCategoryDTO createRewardCategory(CreateRewardDTO r) {
        RewardCategory rc = new RewardCategory(r.getName(), r.getPointsNeeded(), r.getType(), r.getDescription(), r.getCount());
        rewardCategoryRepository.save(rc);
        // RewardMedia rm = mediaService.saveRewardImage(r.getMedia(), rc);
//        Reward newReward = new Reward(r.getName(), r.getPointsNeeded(), r.getBarcodeSerialNo(), r.getType(), r.getDescription());
        return getCleanRewardCategory(rc);
    }


    public List<CleanRewardsCategoryDTO> getAllRewardCategories() {
        List<RewardCategory> rewardCategories = rewardCategoryRepository.findAll();
        List<CleanRewardsCategoryDTO> cleanRewardsCategoryDTOList = new ArrayList<>();
        for (RewardCategory rc : rewardCategories) {
            cleanRewardsCategoryDTOList.add(getCleanRewardCategory(rc));
        }
        return cleanRewardsCategoryDTOList;
    }

    public RewardCategory getRewardCategory(int rewardId) {
        return rewardCategoryRepository.findById(rewardId).orElse(null);
    }
    
    public CleanRewardsCategoryDTO getCleanRewardCategory(int rewardId) {
        RewardCategory rc = rewardCategoryRepository.findById(rewardId).orElse(null);
        if (rc == null) {
            return null;
        }
        return getCleanRewardCategory(rc);
    }

    public CleanRewardsCategoryDTO updateRewardCategory(CreateRewardDTO r, int rewardId) {
        RewardCategory currentReward = rewardCategoryRepository.findById(rewardId).orElse(null);

        if (currentReward == null) {
            return null;
        }

        currentReward.setName(r.getName());
        currentReward.setPointsNeeded(r.getPointsNeeded());
        currentReward.setType(r.getType());
        currentReward.setDescription(r.getDescription());
        currentReward.setCount(r.getCount());

        return getCleanRewardCategory(rewardCategoryRepository.save(currentReward));
    }

    public CleanRewardsCategoryDTO deleteRewardCategory(int rewardId) {
        RewardCategory reward = getRewardCategory(rewardId);
        if (reward == null) {
            return null;
        }
        try {
            rewardBarcodeRepository.deleteRewardBarcodesByRewardCategory(reward);
            rewardCategoryRepository.delete(reward);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return getCleanRewardCategory(reward);
    }

    public List<RewardBarcode> uploadBarcodes(RewardCategory rc, MultipartFile file) {
        BufferedReader br = null;
        List<RewardBarcode> bcList = new ArrayList<>();
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                line = line.replace("ï»¿", "");
                RewardBarcode bc = new RewardBarcode(line.split(",")[0], rc, line.split(",")[1]);
                rewardBarcodeRepository.save(bc);
                bcList.add(bc);
            }
            System.out.println("end");
        } catch (Exception e) {
            return null;
        }
        return bcList;
    }

    public RewardBarcode getRewardBarcode(int id) {
        return rewardBarcodeRepository.findById(id).orElse(null);
    }

    public RewardBarcode deleteBarcode(int id) {
        RewardBarcode rb = rewardBarcodeRepository.findById(id).orElse(null);
        if (rb == null) {
            return null;
        }
        rewardBarcodeRepository.delete(rb);
        return rb;
    }

    @Transactional()
    public String redeemReward(RewardCategory rc, Volunteer v) {
        RewardBarcode reward = rewardBarcodeRepository.findById(rc.getNextAvailableIndex()).orElse(null);
        if (reward == null || reward.isRedeemed()) {
            return "Reward not available";
        }
        int vPoint = v.getPoints();
        Set<RewardBarcode> vRewards = v.getRedeemedRewards();
        if (vPoint >= rc.getPointsNeeded()) {
            reward.setVolunteer(v); // sets redeemed to true
            rewardBarcodeRepository.save(reward);

            v.setPoints(vPoint-rc.getPointsNeeded());
            vRewards.add(reward);
            userRepository.save(v);

            rc.setNextAvailableIndex(rc.getNextAvailableIndex()+1);
            rewardCategoryRepository.save(rc);
            return "Reward redeemed";
        }
        return "Not enough points";
    }

    public RewardBarcode viewRewardBarcode(int rewardId, Volunteer v) {
        RewardBarcode rb = rewardBarcodeRepository.findById(rewardId).orElse(null);
        Set<RewardBarcode> vRewards = v.getRedeemedRewards();
        if (vRewards.contains(rb)) {
            return rb;
        }
        return null;
    }
}