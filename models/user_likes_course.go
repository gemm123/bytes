package models

import "github.com/google/uuid"

type UserLikeCourse struct {
	UserId   uuid.UUID `gorm:"type:uuid;primaryKey"`
	CourseId uuid.UUID `gorm:"type:uuid;primaryKey"`
}
