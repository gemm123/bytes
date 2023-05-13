package repository

import (
	"github.com/gemm123/bytes/models"
	"gorm.io/gorm"
)

type repositoryCourse struct {
	DB *gorm.DB
}

type RepositoryCourse interface {
	GetAllCourse() ([]models.Course, error)
	GetAllCourseMaterial() ([]models.CourseMaterial, error)
	GetCourseById(courseId string) (models.Course, error)
	GetMaterialByCourseId(courseId string) (models.Material, error)
}

func NewRepositoryCourse(DB *gorm.DB) *repositoryCourse {
	return &repositoryCourse{DB: DB}
}

func (r *repositoryCourse) GetAllCourse() ([]models.Course, error) {
	var courses []models.Course
	err := r.DB.Find(&courses).Error
	return courses, err
}

func (r *repositoryCourse) GetAllCourseMaterial() ([]models.CourseMaterial, error) {
	var courseMaterials []models.CourseMaterial
	query := `select c.id as course_id ,c.title, c.description, c.tag, s."text" as summary
		from courses c 
		inner join summaries s 
		on c.id = s.course_id`
	err := r.DB.Raw(query).Scan(&courseMaterials).Error
	return courseMaterials, err
}

func (r *repositoryCourse) GetCourseById(courseId string) (models.Course, error) {
	var course models.Course
	err := r.DB.Where("id = ?", courseId).First(&course).Error
	return course, err
}

func (r *repositoryCourse) GetMaterialByCourseId(courseId string) (models.Material, error) {
	var material models.Material
	err := r.DB.Where("course_id = ?", courseId).First(&material).Error
	return material, err
}
