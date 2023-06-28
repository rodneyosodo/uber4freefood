package models
 import (
	"fmt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/spf13/viper"
)

 func SetupModels () *gorm.DB {
	viper.AutomaticEnv()
	//read .env file
	viper_user := viper.Get("POSTGRES_USER")
 	viper_password := viper.Get("POSTGRES_PASSWORD")
	viper_db := viper.Get("POSTGRES_DB")
	viper_host := viper.Get("POSTGRES_HOST")
	viper_port := viper.Get("POSTGRES_PORT")
	//formart
	prosgret_conname := fmt.Sprintf("host=%v port=%v user=%v dbname=%v password=%v sslmode=disable", viper_host, viper_port, viper_user, viper_db, viper_password)
	fmt.Println("conname is\t\t", prosgret_conname)
	db, err := gorm.Open("postgres", prosgret_conname)
	if err != nil {
		panic("Failed to connect to database!")
	}
	db.AutoMigrate(&Food{})
	return db
}