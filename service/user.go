package service

import (
	"github.com/gemm123/bytes/helper"
	"github.com/gemm123/bytes/models"
	"github.com/gemm123/bytes/repository"
)

type serviceUser struct {
	repositoryUser repository.RepositoryUser
}

type ServiceUser interface {
	Register(input models.Register) error
}

func NewServiceUser(repositoryUser repository.RepositoryUser) *serviceUser {
	return &serviceUser{
		repositoryUser: repositoryUser,
	}
}

func (s *serviceUser) Register(input models.Register) error {
	hashedPass, err := helper.HashPassword(input.Password)
	if err != nil {
		return err
	}

	newUser := models.User{
		Email:    input.Email,
		Username: input.Username,
		Password: hashedPass,
	}

	err = s.repositoryUser.CreateUser(newUser)
	if err != nil {
		return err
	}

	return nil
}
