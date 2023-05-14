package routes

import (
	"github.com/gemm123/bytes/controller"
	"github.com/gemm123/bytes/middleware"
	"github.com/gemm123/bytes/repository"
	"github.com/gemm123/bytes/service"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

func InitRoutes(db *gorm.DB) *gin.Engine {
	route := gin.Default()
	route.Static("/image", "./src/images")

	userRepository := repository.NewRepositoryUser(db)
	userService := service.NewServiceUser(userRepository)
	userController := controller.NewControllerUser(userService)

	userCourseRepository := repository.NewRepositoryUserCourse(db)
	userCourseService := service.NewServiceUserCourse(userCourseRepository)
	userCoruseController := controller.NewControllerUserCourse(userCourseService)

	courseRepository := repository.NewRepositoryCourse(db)
	courseService := service.NewServiceCourse(courseRepository)
	courseController := controller.NewControllerCourse(courseService, userCourseService)

	api := route.Group("/api/v1")

	auth := api.Group("/auth")
	auth.POST("/register", userController.Register)
	auth.POST("/login", userController.Login)
	auth.GET("/user", middleware.CheckAuthorization(), userController.User)

	like := api.Group("/like")
	like.POST("/:courseId", middleware.CheckAuthorization(), userCoruseController.Like)
	like.DELETE("/:courseId", middleware.CheckAuthorization(), userCoruseController.DeleteLike)

	course := api.Group("/course")
	course.GET("/", middleware.CheckAuthorization(), courseController.GetCourses)
	course.GET("/material/:courseId", middleware.CheckAuthorization(), courseController.GetMaterialByCourseId)
	course.GET("/quiz/:courseId", middleware.CheckAuthorization(), courseController.GetQuizByCourseId)
	course.GET("/summary/:courseId", middleware.CheckAuthorization(), courseController.GetSummaryByCourseId)

	return route
}
