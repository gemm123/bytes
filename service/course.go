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