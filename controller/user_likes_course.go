package controller

import (
	"net/http"

	"github.com/gemm123/bytes/service"
	"github.com/gin-gonic/gin"
)

type controllerUserCourse struct {
	serviceUserCourse service.ServiceUserCourse
}

func NewControllerUserCourse(serviceUserCourse service.ServiceUserCourse) *controllerUserCourse {
	return &controllerUserCourse{serviceUserCourse: serviceUserCourse}
}

func (ctr *controllerUserCourse) Like(c *gin.Context) {
	courseId := c.Param("courseId")
	userId := c.MustGet("userID").(string)

	err := ctr.serviceUserCourse.Like(userId, courseId)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "failed " + err.Error(),
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"message": "success like course",
	})
}

func (ctr *controllerUserCourse) DeleteLike(c *gin.Context) {
	courseId := c.Param("courseId")
	userId := c.MustGet("userID").(string)

	err := ctr.serviceUserCourse.DeleteLike(userId, courseId)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "failed " + err.Error(),
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"message": "success delete like course",
	})
}
