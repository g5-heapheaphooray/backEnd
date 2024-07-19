package com.example.demo.repository;

import com.example.demo.model.RewardBarcode;
import com.example.demo.model.RewardCategory;
import com.example.demo.model.User;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface RewardBarcodeRepository extends JpaRepository<RewardBarcode, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<RewardBarcode> findById(Integer id);

    public List<RewardBarcode> findByRewardCategory(RewardCategory rc);

    @Transactional
    List<RewardBarcode> deleteRewardBarcodesByRewardCategory(RewardCategory rc);
}
