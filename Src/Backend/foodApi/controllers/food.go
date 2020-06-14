package controllers

import (
	models "foodApi/models"
	"net/http"
	"github.com/gin-gonic/gin"
	"github.com/jinzhu/gorm"
)

//get /food (all food)
func FindFood(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)

	var foods []models.Food
	db.Find(&foods)

	c.JSON(http.StatusOK, gin.H{"data": foods})
}
//create new food post
func CreateFood(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)

	var input models.CreateFoodInput
	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	//create food post
	food := models.Food{FoodName: input.FoodName, DietType: input.DietType,	Description: input.Description,	SpecialIngridients: input.SpecialIngridients, Serving: input.Serving,	SpecialNote: input.SpecialNote,	FoodImageId: input.FoodImageId,	LocationLat: input.LocationLat,	LocationLong: input.LocationLong, Status: input.Status}
	db.Create(&food)
	c.JSON(http.StatusOK, gin.H{"data": food})
}

//find food /food/:id
func FindFoodwID(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)

	//get model
	var food models.Food
	if err := db.Where("id = ?", c.Param("id")).First(&food).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Record not found!"})
		return
	}
	c.JSON(http.StatusOK, gin.H{"data": food})
}
//update food status
func UpdateFood(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)
	//get model
	var food models.Food
	if err := db.Where("id = ?", c.Param("id")).First(&food).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Record not found!"})
		return
	}
	var input models.UpdateStatusInput
	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	db.Model(&food).Updates(input)
	c.JSON(http.StatusOK, gin.H{"data": food})
}
//delete food /food/:id
func DeleteFood(c *gin.Context) {
	db := c.MustGet("db").(*gorm.DB)
	//get model
	var food models.Food
	if err := db.Where("id = ?", c.Param("id")).First(&food).Error; err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Record not found!"})
		return
	}
	db.Delete(&food)
	c.JSON(http.StatusOK, gin.H{"data": true})
}