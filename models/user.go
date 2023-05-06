package models

import "github.com/google/uuid"

type User struct {
	Id       uuid.UUID `gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	Email    string    `gorm:"unique"`
	Username string
	Password string
	Courses  []Course `gorm:"many2many:user_like_courses;"`
}

type Register struct {
	Email    string `json:"email" binding:"required" `
	Username string `json:"username" binding:"required"`
	Password string `json:"password" binding:"required"`
}

type Login struct {
	Email    string `json:"email" binding:"required"`
	Password string `json:"password" binding:"required"`
}

type UserResponse struct {
	Email    string `json:"email"`
	Username string `json:"username"`
}
