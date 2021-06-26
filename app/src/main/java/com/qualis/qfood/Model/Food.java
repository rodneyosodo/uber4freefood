package com.qualis.qfood.Model;

public class Food {


    private long ID;
    private String foodName;
    private String dietType;

    private String angelUserID;
    private String humanUserID;

    private String description;
    private String specialIngridients;
    private String serving;
    private String SpecialNote;
    private String foodImageId;

    private String locationLat;
    private String locationLong;

    private String CreatedAt;
    private String DeletedAt;
    private String UpdatedAt;

    private String status;


    public Food() {
    }

    public Food(long ID, String foodName, String dietType, String angelUserID, String humanUserID, String description, String specialIngridients, String serving, String specialNote, String foodImageId, String locationLat, String locationLong, String createdAt, String deletedAt, String updatedAt, String status) {
        this.ID = ID;
        this.foodName = foodName;
        this.dietType = dietType;
        this.angelUserID = angelUserID;
        this.humanUserID = humanUserID;
        this.description = description;
        this.specialIngridients = specialIngridients;
        this.serving = serving;
        this.SpecialNote = specialNote;
        this.foodImageId = foodImageId;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.CreatedAt = createdAt;
        this.DeletedAt = deletedAt;
        this.UpdatedAt = updatedAt;
        this.status = status;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public String getAngelUserID() {
        return angelUserID;
    }

    public void setAngelUserID(String angelUserID) {
        this.angelUserID = angelUserID;
    }

    public String getHumanUserID() {
        return humanUserID;
    }

    public void setHumanUserID(String humanUserID) {
        this.humanUserID = humanUserID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialIngridients() {
        return specialIngridients;
    }

    public void setSpecialIngridients(String specialIngridients) {
        this.specialIngridients = specialIngridients;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getSpecialNote() {
        return SpecialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.SpecialNote = specialNote;
    }

    public String getFoodImageId() {
        return foodImageId;
    }

    public void setFoodImageId(String foodImageId) {
        this.foodImageId = foodImageId;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.CreatedAt = createdAt;
    }

    public String getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.DeletedAt = deletedAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.UpdatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
