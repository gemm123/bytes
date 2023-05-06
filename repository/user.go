package repository

import (
	"github.com/gemm123/bytes/models"
	"gorm.io/gorm"
)

type repositoryUser struct {
	DB *gorm.DB
}

type RepositoryUser interface {
	CreateUser(user models.User) error
}

func NewRepositoryUser(DB *gorm.DB) *repositoryUser {
	return &repositoryUser{DB: DB}
}

func (r *repositoryUser) CreateUser(user models.User) error {
	err := r.DB.Create(&user).Error
	return err
}
