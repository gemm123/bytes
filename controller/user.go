package controller

import (
	"net/http"

	"github.com/gemm123/bytes/models"
	"github.com/gemm123/bytes/service"
	"github.com/gin-gonic/gin"
)

type controllerUser struct {
	serviceUser service.ServiceUser
}

func NewControllerUser(serviceUser service.ServiceUser) *controllerUser {
	return &controllerUser{serviceUser: serviceUser}
}

func (ctr *controllerUser) Register(c *gin.Context) {
	var input models.Register
	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	if err := ctr.serviceUser.Register(input); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"message": "register success",
	})
}

func (ctr *controllerUser) Login(c *gin.Context) {
	var input models.Login
	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	err := ctr.serviceUser.CheckAccount(input)
	if err != nil {
		c.JSON(http.StatusUnauthorized, gin.H{
			"message": "failed " + err.Error(),
		})
		return
	}

	token, err := ctr.serviceUser.GenerateToken(input)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "failed " + err.Error(),
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"message": "success",
		"token":   token,
	})
}
