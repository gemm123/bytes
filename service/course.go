package service

import (
	"github.com/gemm123/bytes/models"
	"github.com/gemm123/bytes/repository"
)

type serviceCourse struct {
	repositoryCourse repository.RepositoryCourse
}

type ServiceCourse interface {
	GetAllCourse() ([]models.Course, error)
	GetAllCourseMaterial() ([]models.CourseMaterial, error)
	GetRecommendCourseForMobile(coursesRecommendation []models.CourseFromML) ([]models.Course, error)
	GetMaterialByCourseId(courseId string) (models.Material, error)
	GetQuizByCourseId(courseId string) (models.Quiz, error)
	GetSummaryByCourseId(courseId string) (models.Summary, error)
}

func NewServiceCourse(repositoryCourse repository.RepositoryCourse) *serviceCourse {
	return &serviceCourse{
		repositoryCourse: repositoryCourse,
	}
}

func (s *serviceCourse) GetAllCourse() ([]models.Course, error) {
	courses, err := s.repositoryCourse.GetAllCourse()
	if err != nil {
		return nil, err
	}

	return courses, nil
}

func (s *serviceCourse) GetAllCourseMaterial() ([]models.CourseMaterial, error) {
	courseMaterials, err := s.repositoryCourse.GetAllCourseMaterial()
	if err != nil {
		return nil, err
	}

	return courseMaterials, nil
}

func (s *serviceCourse) GetRecommendCourseForMobile(coursesRecommendation []models.CourseFromML) ([]models.Course, error) {
	var courseResponse []models.Course
	for _, courseRecommendation := range coursesRecommendation {
		course, err := s.repositoryCourse.GetCourseById(courseRecommendation.CourseId)
		if err != nil {
			return nil, err
		}
		courseResponse = append(courseResponse, course)
	}
	return courseResponse, nil
}

func (s *serviceCourse) GetMaterialByCourseId(courseId string) (models.Material, error) {
	material, err := s.repositoryCourse.GetMaterialByCourseId(courseId)
	return material, err
}

func (s *serviceCourse) GetQuizByCourseId(courseId string) (models.Quiz, error) {
	quiz, err := s.repositoryCourse.GetQuizByCourseId(courseId)
	return quiz, err
}

func (s *serviceCourse) GetSummaryByCourseId(courseId string) (models.Summary, error) {
	summary, err := s.repositoryCourse.GetSummaryByCourseId(courseId)
	return summary, err
}
