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
	Id       string `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	CourseId string `gorm:"type:uuid" json:"courseId"`
	Place    int    `json:"place"`
	Title    string `json:"title"`
	Image    string `json:"image"`
	Text     string `json:"text"`
}

type Quiz struct {
	Id       string `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	CourseId string `gorm:"type:uuid" json:"courseId"`
	Place    int    `json:"place"`
	Question string `json:"question"`
	Choice   string `json:"choice"`
	Key      string `json:"key"`
}

type Summary struct {
	Id       string `gorm:"type:uuid;default:gen_random_uuid();primaryKey" json:"id"`
	CourseId string `gorm:"type:uuid" json:"courseId"`
	Place    int    `json:"place"`
	Text     string `json:"text"`
}

type CourseToML struct {
	UserId         string
	CourseId       string
	UserLikeCourse bool
	Title          string
	Description    string
	Tag            string
	Summary        string
}

type SendCourseToML struct {
	Data []CourseToML `json:"data"`
}

type CourseFromML struct {
	CourseId string
	Title    string
}

type CourseMaterial struct {
	CourseId    string `gorm:"column:course_id"`
	Title       string `gorm:"column:title"`
	Description string `gorm:"column:description"`
	Tag         string `gorm:"column:tag"`
	Summary     string `gorm:"column:summary"`
}

type CourseToMobile struct {
	Id          string `json:"id"`
	Title       string `json:"title"`
	Description string `json:"description"`
	Thumbnail   string `json:"thumbnail"`
	Tag         string `json:"tag"`
	UserLike    bool   `json:"userLike"`
}
