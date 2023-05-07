package service

import (
	"github.com/gemm123/bytes/models"
	"github.com/gemm123/bytes/repository"
)

type serviceUserCourse struct {
	repositoryUserCourse repository.RepositoryUserCourse
}

type ServiceUserCourse interface {
	Like(userId, courseId string) error
	DeleteLike(userId, courseId string) error
}

func NewServiceUserCourse(repositoryUserCourse repository.RepositoryUserCourse) *serviceUserCourse {
	return &serviceUserCourse{
		repositoryUserCourse: repositoryUserCourse,
	}
}

func (s *serviceUserCourse) Like(userId, courseId string) error {
	userLikeCourse := models.UserLikeCourse{
		UserId:   userId,
		CourseId: courseId,
	}

	err := s.repositoryUserCourse.Insert(userLikeCourse)

	return err
}

func (s *serviceUserCourse) DeleteLike(userId, courseId string) error {
	userLikeCourse := models.UserLikeCourse{
		UserId:   userId,
		CourseId: courseId,
	}

	_, err := s.repositoryUserCourse.FindLike(userLikeCourse)
	if err != nil {
		return err
	}

	err = s.repositoryUserCourse.Delete(userLikeCourse)
	if err != nil {
		return err
	}

	return nil
}
