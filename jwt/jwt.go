package jwt

import (
	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/google/uuid"
)

var JwtKey = os.Getenv("JWT_KEY")

type CustomClaims struct {
	Id       uuid.UUID
	Email    string
	Username string
	jwt.RegisteredClaims
}

func GenerateToken(Id uuid.UUID, email, username string) (string, error) {
	claims := CustomClaims{
		Id:       Id,
		Email:    email,
		Username: username,
		RegisteredClaims: jwt.RegisteredClaims{
			ExpiresAt: jwt.NewNumericDate(time.Now().Add(time.Hour * 168)),
			Issuer:    "bytes",
		},
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	signedToken, err := token.SignedString([]byte(JwtKey))
	return signedToken, err
}
