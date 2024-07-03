package com.example.demo.dto.models;

public class CleanRewardsBarcodeDTO {
    private int reward_barcode_id;

    private String barcode;

    private boolean redeemed;

    private String expiryDate;

    private String volunteer_email;

    private int reward_category_id;
    private String name;
    private int pointsNeeded;

    private String type;

    private String description;

    public CleanRewardsBarcodeDTO(int reward_barcode_id, String barcode, boolean redeemed, String expiryDate, String volunteer_email, int reward_category_id, String name, int pointsNeeded, String type, String description) {
        this.reward_barcode_id = reward_barcode_id;
        this.barcode = barcode;
        this.redeemed = redeemed;
        this.expiryDate = expiryDate;
        this.volunteer_email = volunteer_email;
        this.reward_category_id = reward_category_id;
        this.name = name;
        this.pointsNeeded = pointsNeeded;
        this.type = type;
        this.description = description;
    }

    public int getReward_barcode_id() {
        return reward_barcode_id;
    }

    public void setReward_barcode_id(int reward_barcode_id) {
        this.reward_barcode_id = reward_barcode_id;
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getVolunteer_email() {
        return volunteer_email;
    }

    public void setVolunteer_email(String volunteer_email) {
        this.volunteer_email = volunteer_email;
    }

    public int getReward_category_id() {
        return reward_category_id;
    }

    public void setReward_category_id(int reward_category_id) {
        this.reward_category_id = reward_category_id;
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
}
