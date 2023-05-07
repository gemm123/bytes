package models

type UserLikeCourse struct {
	UserId   string `gorm:"type:uuid"`
	CourseId string `gorm:"type:uuid"`
}
