package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reward_barcodes")
public class RewardBarcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "redeemed")
    private boolean redeemed;

    @Column(name = "expiry_date")
    private String expiryDate;

    @ManyToOne
    @JoinColumn(name = "reward_cat_id", nullable = false)
    private RewardCategory rewardCategory;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    public RewardBarcode() {
    }

    public RewardBarcode(String barcode, RewardCategory rewardCategory, String expiryDate) {
        this.barcode = barcode;
        this.rewardCategory = rewardCategory;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

    public RewardCategory getRewardCategory() {
        return rewardCategory;
    }

    public void setRewardCategory(RewardCategory rewardCategory) {
        this.rewardCategory = rewardCategory;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
        setRedeemed(true);
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RewardBarcode) {
            return ((RewardBarcode) obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}