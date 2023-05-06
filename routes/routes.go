package routes

import "github.com/gin-gonic/gin"

func InitRoutes() *gin.Engine {
	route := gin.Default()

	route.GET("/ping", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "pong",
		})
	})

	return route
}
