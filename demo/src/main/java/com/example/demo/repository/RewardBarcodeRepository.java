package com.example.demo.repository;

import com.example.demo.model.RewardBarcode;
import com.example.demo.model.RewardCategory;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardBarcodeRepository extends JpaRepository<RewardBarcode, Integer> {
    public List<RewardBarcode> findByRewardCategory(RewardCategory rc);

    @Transactional
    List<RewardBarcode> deleteRewardBarcodesByRewardCategory(RewardCategory rc);

//    @Modifying
//    @Query(value = "delete from reward_barcodes where reward_barcodes.rewardCategory = :rewardCategory", nativeQuery = true)
//    int deleteRewardBarcodesByRewardCategory

//    @Query(value = "DELETE FROM events_part WHERE events_part.user_id = :userId", nativeQuery = true)
    // String deleteParticipant(@Param("userId") String userId);
}
