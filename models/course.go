package models

import "github.com/google/uuid"

type Course struct {
	Id          uuid.UUID `gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	Title       string
	Description string
	Thumbnail   string
	Tag         string
	Like        int
	Users       []User `gorm:"many2many:user_like_courses;"`
}
