package models

import "github.com/google/uuid"

type Course struct {
	Id             uuid.UUID `gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	Title          string
	Description    string
	Thumbnail      string
	Tag            string
	Like           int
	Material       Material `gorm:"foreignKey:CourseId"`
	Quiz           Quiz     `gorm:"foreignKey:CourseId"`
	Summary        Summary  `gorm:"foreignKey:CourseId"`
	Users          []User   `gorm:"many2many:user_like_courses;"`
	UserLikeCourse []UserLikeCourse
}

type Material struct {
	Id       string `gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	CourseId string `gorm:"type:uuid"`
	Place    int
	Title    string
	Image    string
	Text     string
}

type Quiz struct {
	Id       string `gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	CourseId string `gorm:"type:uuid"`
	Place    int
	Question string
	Choice   string
	Key      string
}

type Summary struct {
	Id       string `gorm:"type:uuid;default:gen_random_uuid();primaryKey"`
	CourseId string `gorm:"type:uuid"`
	Place    int
	Title    string
	Text     string
}
