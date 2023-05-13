package controller

import (
	"encoding/json"
	"fmt"
	"net/http"
	"strings"

	"github.com/gemm123/bytes/models"
	"github.com/gemm123/bytes/service"
	"github.com/gin-gonic/gin"
)

type controllerCourse struct {
	serviceCourse     service.ServiceCourse
	serviceUserCourse service.ServiceUserCourse
}

func NewControllerCourse(serviceCourse service.ServiceCourse, serviceUserCourse service.ServiceUserCourse) *controllerCourse {
	return &controllerCourse{serviceCourse: serviceCourse, serviceUserCourse: serviceUserCourse}
}

func (ctr *controllerCourse) GetCourses(c *gin.Context) {
	userId := c.MustGet("userID").(string)

	courseMaterials, err := ctr.serviceCourse.GetAllCourseMaterial()
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	coursesToML := make([]models.CourseToML, len(courseMaterials))

	for i, courseMaterial := range courseMaterials {
		tags := strings.Split(courseMaterial.Tag, ",")

		ok, _ := ctr.serviceUserCourse.CheckLike(userId, courseMaterial.CourseId)

		if ok {
			coursesToML[i].UserLikeCourse = true
		}

		coursesToML[i].UserId = userId
		coursesToML[i].CourseId = courseMaterial.CourseId
		coursesToML[i].Title = courseMaterial.Title
		coursesToML[i].Description = courseMaterial.Description
		coursesToML[i].Tag = append(coursesToML[i].Tag, tags...)
		coursesToML[i].Summary = courseMaterial.Summary
	}

	sendCourseToML := models.SendCourseToML{
		Data: coursesToML,
	}

	jsonCourseToML, err := json.Marshal(sendCourseToML)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	fmt.Println(string(jsonCourseToML))

	c.JSON(http.StatusOK, gin.H{
		"message": "success",
		"data":    coursesToML,
	})
}
