package repository

import (
	"github.com/gemm123/bytes/models"
	"gorm.io/gorm"
)

type repositoryUserCourse struct {
	DB *gorm.DB
}

type RepositoryUserCourse interface {
	Insert(input models.UserLikeCourse) error
	FindLike(input models.UserLikeCourse) (models.UserLikeCourse, error)
	Delete(input models.UserLikeCourse) error
}

func NewRepositoryUserCourse(DB *gorm.DB) *repositoryUserCourse {
	return &repositoryUserCourse{DB: DB}
}

func (r *repositoryUserCourse) Insert(input models.UserLikeCourse) error {
	err := r.DB.Exec(`INSERT INTO public.user_like_courses (course_id, user_id)
	VALUES(?, ?)`, input.CourseId, input.UserId).Error
	return err
}

func (r *repositoryUserCourse) FindLike(input models.UserLikeCourse) (models.UserLikeCourse, error) {
	var userLikeCourse models.UserLikeCourse
	err := r.DB.Where("course_id = ? AND user_id = ?", input.CourseId, input.UserId).First(&userLikeCourse).Error
	return userLikeCourse, err
}

func (r *repositoryUserCourse) Delete(input models.UserLikeCourse) error {
	err := r.DB.Where("course_id = ? AND user_id = ?", input.CourseId, input.UserId).Delete(&input).Error
	return err
}
