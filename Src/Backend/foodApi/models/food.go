package models

import "time"

type Food struct {
	ID                 uint   `json:"id" gorm:"primary_key"`
	FoodName           string `json:"foodName"`
	DietType           string `json:"dietType"`
	Description        string `json:"description"`
	SpecialIngridients string `json:"specialIngridients"`
	Serving            string `json:"serving"`
	SpecialNote        string `json:"specialNote"`
	FoodImageId        string `json:"foodImageId"`
	LocationLat        string `json:"locationLat"`
	LocationLong       string `json:"locationLong"`
	AngelUserID        string `json:"angelUserID"`
	HumanUserID        string `json:"humanUserID"`
	Status             string `json:"status"`
	CreatedAt          time.Time
	UpdatedAt          time.Time
	DeletedAt          *time.Time
}
type CreateFoodInput struct {
	FoodName           string `json:"foodName" binding:"required"`
	DietType           string `json:"dietType"`
	Description        string `json:"description" binding:"required"`
	SpecialIngridients string `json:"specialIngridients"`
	Serving            string `json:"serving"`
	SpecialNote        string `json:"specialNote"`
	FoodImageId        string `json:"foodImageId"`
	LocationLat        string `json:"locationLat" binding:"required"`
	LocationLong       string `json:"locationLong" binding:"required"`
	AngelUserID        string `json:"angelUserID"`
	HumanUserID        string `json:"humanUserID"`
	Status             string `json:"status"`
}
type UpdateStatusInput struct {
	FoodName           string `json:"foodName"`
	DietType           string `json:"dietType"`
	Description        string `json:"description"`
	SpecialIngridients string `json:"specialIngridients"`
	Serving            string `json:"serving"`
	SpecialNote        string `json:"specialNote"`
	FoodImageId        string `json:"foodImageId"`
	LocationLat        string `json:"locationLat"`
	LocationLong       string `json:"locationLong"`
	AngelUserID        string `json:"angelUserID"`
	HumanUserID        string `json:"humanUserID"`
	Status             string `json:"status"`
}
