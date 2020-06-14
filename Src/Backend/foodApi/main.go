package main

import (
	controllers "foodApi/controllers"
	"foodApi/models"

	"github.com/gin-gonic/gin"
)
func main() {
	r := gin.Default()
	db := models.SetupModels()
	r.Use(func(c *gin.Context) {
		c.Set("db", db)
		c.Next()
	})

	r.GET("/food", controllers.FindFood)

	r.POST("/food", controllers.CreateFood)

	r.GET("/food/:id", controllers.FindFoodwID)
	
	r.PATCH("/food/:id", controllers.UpdateFood)

	r.DELETE("/food/:id", controllers.DeleteFood)

	r.Run()
}