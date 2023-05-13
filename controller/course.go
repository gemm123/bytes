package controller

import (
	"bytes"
	"encoding/json"
	"log"
	"net/http"

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

		ok, _ := ctr.serviceUserCourse.CheckLike(userId, courseMaterial.CourseId)

		if ok {
			coursesToML[i].UserLikeCourse = true
		}

		coursesToML[i].UserId = userId
		coursesToML[i].CourseId = courseMaterial.CourseId
		coursesToML[i].Title = courseMaterial.Title
		coursesToML[i].Description = courseMaterial.Description
		coursesToML[i].Tag = courseMaterial.Tag
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

	req, err := http.NewRequest("POST", "http://103.67.186.184:5000/get-recommendation", bytes.NewBuffer(jsonCourseToML))
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	req.Header.Set("Content-Type", "application/json")

	client := http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		log.Fatal(err)
	}
	defer resp.Body.Close()

	var coursesFromML []models.CourseFromML
	if err := json.NewDecoder(resp.Body).Decode(&coursesFromML); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	courseResponse, err := ctr.serviceCourse.GetRecommendCourseForMobile(coursesFromML)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	coursesToMobile := make([]models.CourseToMobile, len(courseResponse))
	for i, cr := range courseResponse {
		ok, _ := ctr.serviceUserCourse.CheckLike(userId, cr.Id.String())
		if ok {
			coursesToMobile[i].UserLike = true
		}

		coursesToMobile[i].Id = cr.Id.String()
		coursesToMobile[i].Title = cr.Title
		coursesToMobile[i].Description = cr.Description
		coursesToMobile[i].Thumbnail = cr.Thumbnail
		coursesToMobile[i].Tag = cr.Tag
	}

	c.JSON(http.StatusOK, gin.H{
		"message": "success",
		"data":    coursesToMobile,
	})
}

func (ctr *controllerCourse) GetMaterialByCourseId(c *gin.Context) {
	courseId := c.Param("courseId")

	material, err := ctr.serviceCourse.GetMaterialByCourseId(courseId)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"message": "failed: " + err.Error(),
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"message": "success",
		"data":    material,
	})
}
