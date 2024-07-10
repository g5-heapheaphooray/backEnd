package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.demo.dto.RewardBarcodesListDTO;
import com.example.demo.dto.models.CleanRewardsBarcodeDTO;
import com.example.demo.dto.models.CleanRewardsCategoryDTO;
import com.example.demo.model.RewardCategory;
import com.example.demo.model.User;
import com.example.demo.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.CreateRewardDTO;
import com.example.demo.dto.RewardsListDTO;
import com.example.demo.model.RewardBarcode;
import com.example.demo.service.RewardService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/reward")
public class RewardController {
    
    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/reward-category/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CleanRewardsCategoryDTO> createReward(@RequestBody CreateRewardDTO dto) {
        CleanRewardsCategoryDTO res = rewardService.createRewardCategory(dto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/reward-category/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RewardsListDTO> allRewards() {
        List<CleanRewardsCategoryDTO> cleanRewardsCategoryDTOList = rewardService.getAllRewardCategories();
        RewardsListDTO res = new RewardsListDTO(cleanRewardsCategoryDTOList);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/reward-category/get/{rewardId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CleanRewardsCategoryDTO> getReward(@PathVariable int rewardId) {
        CleanRewardsCategoryDTO res = rewardService.getCleanRewardCategory(rewardId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/reward-category/update/{rewardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CleanRewardsCategoryDTO> updateReward(@PathVariable int rewardId, @RequestBody CreateRewardDTO dto) {
        CleanRewardsCategoryDTO res = rewardService.updateRewardCategory(dto, rewardId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/reward-category/delete/{rewardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CleanRewardsCategoryDTO> deleteReward(@PathVariable int rewardId) {
        CleanRewardsCategoryDTO res = rewardService.deleteRewardCategory(rewardId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value="/reward-category/upload-barcodes/{rewardId}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CleanRewardsBarcodeDTO>> uploadReward(@PathVariable int rewardId, @RequestPart("file") MultipartFile file) {
        System.out.println("helloo");
        RewardCategory rc = rewardService.getRewardCategory(rewardId);
        List<RewardBarcode> rbs = rewardService.uploadBarcodes(rc, file);
        List<CleanRewardsBarcodeDTO> res = new ArrayList<>();
        for (RewardBarcode rb : rbs) {
            String vemail = null;
            if (rb.getVolunteer() != null) {
                vemail = rb.getVolunteer().getEmail();
            }
            CleanRewardsBarcodeDTO crb = new CleanRewardsBarcodeDTO(rb.getId(), rb.getBarcode(), rb.isRedeemed(), rb.getExpiryDate(), vemail, 
                    rc.getId(), rc.getName(), rc.getPointsNeeded(), rc.getType(), rc.getDescription());
            res.add(crb);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/reward/delete/{rewardCatId}/{rewardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CleanRewardsBarcodeDTO> uploadReward(@PathVariable int rewardCatId, @PathVariable int rewardId) {
        RewardCategory rc = rewardService.getRewardCategory(rewardId);
        RewardBarcode rb = rewardService.deleteBarcode(rewardId);
        if (rc == null || rb == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        String vemail = null;
        if (rb.getVolunteer() != null) {
            vemail = rb.getVolunteer().getEmail();
        }
        CleanRewardsBarcodeDTO crb = new CleanRewardsBarcodeDTO(rb.getId(), rb.getBarcode(), rb.isRedeemed(), rb.getExpiryDate(), vemail,
                rc.getId(), rc.getName(), rc.getPointsNeeded(), rc.getType(), rc.getDescription());
        return new ResponseEntity<>(crb, HttpStatus.OK);
    }

    @GetMapping("/reward/redeem/{rewardCatId}")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<String> redeemReward(@PathVariable int rewardCatId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        RewardCategory rc = rewardService.getRewardCategory(rewardCatId);
        String s = rewardService.redeemReward(rc, (Volunteer) user);
        System.out.println(s);
        if (s.equals("Reward redeemed")) {
            return new ResponseEntity<>(s, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(s, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/reward/all-redeemed")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<RewardBarcodesListDTO> redeemedReward() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Set<RewardBarcode> vRewards = ((Volunteer) user).getRedeemedRewards();
        List<CleanRewardsBarcodeDTO> rList = new ArrayList<>();
        for (RewardBarcode rb : vRewards) {
            RewardCategory rc = rb.getRewardCategory();
            CleanRewardsBarcodeDTO crb = new CleanRewardsBarcodeDTO(rb.getId(), rb.getBarcode(), rb.isRedeemed(), rb.getExpiryDate(), user.getEmail(),
                    rc.getId(), rc.getName(), rc.getPointsNeeded(), rc.getType(), rc.getDescription());
            rList.add(crb);
        }
        RewardBarcodesListDTO res = new RewardBarcodesListDTO(rList);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/reward/use/{rewardId}")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<CleanRewardsBarcodeDTO> useRewardBarcode(@PathVariable int rewardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        RewardBarcode rb = rewardService.useRewardBarcode(rewardId, (Volunteer) user);
        if (rb == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        RewardCategory rc = rb.getRewardCategory();
        CleanRewardsBarcodeDTO crb = new CleanRewardsBarcodeDTO(rb.getId(), rb.getBarcode(), rb.isRedeemed(), rb.getExpiryDate(), user.getEmail(),
                rc.getId(), rc.getName(), rc.getPointsNeeded(), rc.getType(), rc.getDescription());
        return new ResponseEntity<>(crb, HttpStatus.OK);
    }



//    @GetMapping("/redeem/{rewardId}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<CleanRewardsDTO> redeemReward(@PathVariable int rewardId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//        RewardCategory r = rewardService.redeemReward(rewardId, (Volunteer) user);
//        CleanRewardsDTO cr = new CleanRewardsDTO(r.getName(), r.getPointsNeeded(), r.getType(), r.getDescription());
//        return new ResponseEntity<>(cr, HttpStatus.OK);
//    }


}
