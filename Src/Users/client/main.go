package main

import (
	"log"

	"qualEATS/userPB"

	"golang.org/x/net/context"
	"google.golang.org/grpc"
)

func main() {
	var conn *grpc.ClientConn
	conn, err := grpc.Dial(":7777", grpc.WithInsecure())
	if err != nil {
		log.Fatalf("did not connect: %s", err)
	}
	defer conn.Close()
	defer conn.Close()
	c := userPB.NewAuthServiceClient(conn)
	response, err := c.Signup(context.Background(), &userPB.RegDetails{Username: "Sam", Password: "qualis", Usertype: "client", Company: "Dummy", Email: "anemailaddress"})
	if err != nil {
		log.Fatalf("Error when calling Login: %s", err)
	}
	log.Printf("Response from server: %s", response.Token)
}
