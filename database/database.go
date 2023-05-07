package database

import (
	"github.com/gemm123/bytes/models"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func InitDB(dsn string) (*gorm.DB, error) {
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})

	db.AutoMigrate(&models.User{}, &models.Course{}, &models.Material{}, &models.Quiz{}, &models.Summary{})
	db.AutoMigrate(&models.UserLikeCourse{})

	return db, err
}

func CloseDB(db *gorm.DB) {
	dbsql, _ := db.DB()
	dbsql.Close()
}
