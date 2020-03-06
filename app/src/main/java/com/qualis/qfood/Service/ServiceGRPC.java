package com.qualis.qfood.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.qualis.qfood.LoginRequest;
import com.qualis.qfood.LoginResponse;
import com.qualis.qfood.userLoginGrpc;


public class ServiceGRPC {


    public static LoginResponse loginService(String serverIP, int port, String email, String password) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIP, port).usePlaintext().build();

        //stubs
        userLoginGrpc.userLoginBlockingStub userLoginStub =  userLoginGrpc.newBlockingStub(channel);

        LoginRequest loginRequest = LoginRequest.newBuilder().setEmail(email).setPassword(password).build();

        LoginResponse loginResponse = userLoginStub.login(loginRequest);
        return loginResponse;
    }


    public static void logoutService(String serverIP, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIP, port).usePlaintext().build();
    }



    public static void signUPService(String serverIP, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIP, port).usePlaintext().build();
    }

}
