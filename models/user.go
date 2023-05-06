package models

import "github.com/google/uuid"

type User struct {
	Id       uuid.UUID `gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	Email    string
	Username string
	Password string
	Courses  []Course `gorm:"many2many:user_like_courses;"`
}
