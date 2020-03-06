package main

import (
	"context"
	"fmt"
	"log"
	"net"
	"time"

	userPB "qualEATS/userPB"

	"github.com/dgrijalva/jwt-go"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"google.golang.org/grpc"
)

type Users struct {
	Id       string `bson:"_id"`
	Username string `bson:"name"`
	Password string `bson:"password"`
}
type Regst struct {
	Username string `bson:"name"`
	Password string `bson:"password"`
	Usertype string `bson:"usertype"`
	Company  string `bson:"company"`
	Email    string `bson:"email"`
}

var jwtKey = []byte("affd25090f0b173ca7ce22837167579e")

type AuthServiceServer struct {
}

type Claims struct {
	Username string `json:"username"`
	jwt.StandardClaims
}

func (s *AuthServiceServer) Login(ctx context.Context, req *userPB.LoginReq) (*userPB.LoginRes, error) {
	uName := req.Username
	pWD := req.Pwad
	expirationTime := time.Now().Add(5 * time.Minute)
	//fmt.Printf("%s", pWD)
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")

	client, err := mongo.Connect(context.TODO(), clientOptions)

	if err != nil {
		log.Fatal(err)
	}

	// Check the connection
	err = client.Ping(context.TODO(), nil)

	if err != nil {
		log.Fatal(err)
	}

	//fmt.Println("Connected to MongoDB!")
	collection := client.Database("qualEATS").Collection("users")
	var result Users

	filter := bson.D{{"name", uName}}
	var ntx int64 = 0

	err = collection.FindOne(context.TODO(), filter).Decode(&result)
	if err != nil {
		ntx = 1
		//log.Fatal(err)
	}

	//fmt.Printf("Found a single document: %+v\n", result.Password)
	if ntx == 0 {
		if pWD == result.Password {
			claims := &Claims{
				Username: uName,
				StandardClaims: jwt.StandardClaims{
					ExpiresAt: expirationTime.Unix(),
				},
			}
			token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
			tokenString, err := token.SignedString(jwtKey)
			if err != nil {
				return &userPB.LoginRes{Token: "Internal Error"}, nil
			}
			return &userPB.LoginRes{Token: tokenString}, nil
		} else {
			return &userPB.LoginRes{Token: "Wrong password"}, nil
		}
	} else {
		return &userPB.LoginRes{Token: "User does not exist"}, nil
	}
}
func (s *AuthServiceServer) Signup(ctx context.Context, req1 *userPB.RegDetails) (*userPB.LoginRes, error) {
	uName1 := req1.Username
	Pwad1 := req1.Password
	uType := req1.Usertype
	comp := req1.Company
	email := req1.Email
	expirationTime := time.Now().Add(5 * time.Minute)
	clientOptions := options.Client().ApplyURI("mongodb://localhost:27017")

	client, err := mongo.Connect(context.TODO(), clientOptions)

	if err != nil {
		log.Fatal(err)
	}

	// Check the connection
	err = client.Ping(context.TODO(), nil)

	if err != nil {
		log.Fatal(err)
	}

	//fmt.Println("Connected to MongoDB!")
	collection := client.Database("qualEATS").Collection("users")
	var result Regst

	filter := bson.D{{"name", uName1}}
	var ntx int64 = 0
	err = collection.FindOne(context.TODO(), filter).Decode(&result)

	if err != nil {
		ntx = 1
		//log.Fatal(err)
	}
	tokenString := ""

	if ntx == 1 {
		newUser := Regst{uName1, Pwad1, uType, comp, email}
		insertResult, err := collection.InsertOne(context.TODO(), newUser)
		if err != nil {
			log.Fatal(err)
		}
		if insertResult.InsertedID != nil {
			claims := &Claims{
				Username: uName1,
				StandardClaims: jwt.StandardClaims{
					ExpiresAt: expirationTime.Unix(),
				},
			}
			token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
			tokenString, err = token.SignedString(jwtKey)
		} else {
			tokenString = "Internal error"
		}
	} else {
		tokenString = "Username exists"
	}
	return &userPB.LoginRes{Token: tokenString}, nil
}
func main() {
	//ctx := context.Background()
	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", 7777))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := AuthServiceServer{}
	grpcServer := grpc.NewServer()
	userPB.RegisterAuthServiceServer(grpcServer, &s)
	if err := grpcServer.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %s", err)
	}

}
