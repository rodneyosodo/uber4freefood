package com.qualis.angelapp.Model;

public class Food {


    private String foodId;
    private String foodName;
    private String dietType;

    private String angelUserID;
    private String humanUserID;

    private String description;
    private String specialIngredients;
    private String serving;
    private String specialNote;
    private String foodImageId;

    private String locationLat;
    private String locationLong;

    private String createdAt;
    private String deletedAt;
    private String updatedAt;

    private String status;


    public Food() {
    }

    public Food(String foodId, String foodName, String dietType, String angelUserID, String humanUserID, String description, String specialIngredients, String serving, String specialNote, String foodImageId, String locationLat, String locationLong, String createdAt, String deletedAt, String updatedAt, String status) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.dietType = dietType;
        this.angelUserID = angelUserID;
        this.humanUserID = humanUserID;
        this.description = description;
        this.specialIngredients = specialIngredients;
        this.serving = serving;
        this.specialNote = specialNote;
        this.foodImageId = foodImageId;
        this.locationLat = locationLat;
        this.locationLong = locationLong;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
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

    public String getSpecialIngredients() {
        return specialIngredients;
    }

    public void setSpecialIngredients(String specialIngredients) {
        this.specialIngredients = specialIngredients;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
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
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
