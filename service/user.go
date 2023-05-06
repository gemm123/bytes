package service

import (
	"errors"

	"github.com/gemm123/bytes/helper"
	"github.com/gemm123/bytes/jwt"
	"github.com/gemm123/bytes/models"
	"github.com/gemm123/bytes/repository"
)

type serviceUser struct {
	repositoryUser repository.RepositoryUser
}

type ServiceUser interface {
	Register(input models.Register) error
	CheckAccount(input models.Login) error
	GenerateToken(input models.Login) (string, error)
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

func (s *serviceUser) CheckAccount(input models.Login) error {
	user, err := s.repositoryUser.FindUserByEmail(input.Email)
	if err != nil {
		return errors.New("email or password not registered")
	}

	ok := helper.CheckPasswordHash(input.Password, user.Password)
	if !ok {
		return errors.New("email or password not registered")
	}

	return err
}

func (s *serviceUser) GenerateToken(input models.Login) (string, error) {
	user, err := s.repositoryUser.FindUserByEmail(input.Email)
	if err != nil {
		return "", err
	}

	token, err := jwt.GenerateToken(user.Id, user.Email, user.Username)
	if err != nil {
		return "", err
	}

	return token, nil
}
