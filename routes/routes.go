package routes

import (
	"github.com/gemm123/bytes/controller"
	"github.com/gemm123/bytes/repository"
	"github.com/gemm123/bytes/service"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

func InitRoutes(db *gorm.DB) *gin.Engine {
	route := gin.Default()

	userRepository := repository.NewRepositoryUser(db)
	userService := service.NewServiceUser(userRepository)
	userController := controller.NewControllerUser(userService)

	api := route.Group("/api/v1")

	auth := api.Group("/auth")
	auth.POST("/register", userController.Register)
	auth.POST("/login", userController.Login)

	return route
}
