package repository

import (
	"gorm.io/gorm"
)

type repositoryUser struct {
	DB *gorm.DB
}

type RepositoryUser interface {
}

func NewRepositoryUser(DB *gorm.DB) *repositoryUser {
	return &repositoryUser{DB: DB}
}

// func (r *repositoryUser) CreateUser(user models.User) err
