package com.qualis.qfood.Service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.qualis.qfood.LoginReq;
import com.qualis.qfood.LoginRes;
import com.qualis.qfood.authServiceGrpc;


public class ServiceGRPC {


    public static LoginRes authService(String serverIP, int port, final String username, final String password) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIP, port).usePlaintext().build();

        authServiceGrpc.authServiceBlockingStub authServiceBlockingStub = authServiceGrpc.newBlockingStub(channel);

        LoginReq loginReq = com.qualis.qfood.LoginReq.newBuilder().setUsername(username).setPwad(password).build();

        LoginRes loginResponse = authServiceBlockingStub.login(loginReq);


        return loginResponse;
    }


    public static void logoutService(String serverIP, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIP, port).usePlaintext().build();
    }



    public static void signUPService(String serverIP, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIP, port).usePlaintext().build();
    }

}
