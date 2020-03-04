package com.cloudMessaging.cloud;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: user.proto")
public final class messagesGrpc {

  private messagesGrpc() {}

  public static final String SERVICE_NAME = "messages";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cloudMessaging.cloud.User.Empty,
      com.cloudMessaging.cloud.User.Text> getMessageInMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "messageIn",
      requestType = com.cloudMessaging.cloud.User.Empty.class,
      responseType = com.cloudMessaging.cloud.User.Text.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cloudMessaging.cloud.User.Empty,
      com.cloudMessaging.cloud.User.Text> getMessageInMethod() {
    io.grpc.MethodDescriptor<com.cloudMessaging.cloud.User.Empty, com.cloudMessaging.cloud.User.Text> getMessageInMethod;
    if ((getMessageInMethod = messagesGrpc.getMessageInMethod) == null) {
      synchronized (messagesGrpc.class) {
        if ((getMessageInMethod = messagesGrpc.getMessageInMethod) == null) {
          messagesGrpc.getMessageInMethod = getMessageInMethod = 
              io.grpc.MethodDescriptor.<com.cloudMessaging.cloud.User.Empty, com.cloudMessaging.cloud.User.Text>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "messages", "messageIn"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudMessaging.cloud.User.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudMessaging.cloud.User.Text.getDefaultInstance()))
                  .setSchemaDescriptor(new messagesMethodDescriptorSupplier("messageIn"))
                  .build();
          }
        }
     }
     return getMessageInMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cloudMessaging.cloud.User.Text,
      com.cloudMessaging.cloud.User.delivery> getSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "send",
      requestType = com.cloudMessaging.cloud.User.Text.class,
      responseType = com.cloudMessaging.cloud.User.delivery.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cloudMessaging.cloud.User.Text,
      com.cloudMessaging.cloud.User.delivery> getSendMethod() {
    io.grpc.MethodDescriptor<com.cloudMessaging.cloud.User.Text, com.cloudMessaging.cloud.User.delivery> getSendMethod;
    if ((getSendMethod = messagesGrpc.getSendMethod) == null) {
      synchronized (messagesGrpc.class) {
        if ((getSendMethod = messagesGrpc.getSendMethod) == null) {
          messagesGrpc.getSendMethod = getSendMethod = 
              io.grpc.MethodDescriptor.<com.cloudMessaging.cloud.User.Text, com.cloudMessaging.cloud.User.delivery>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "messages", "send"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudMessaging.cloud.User.Text.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cloudMessaging.cloud.User.delivery.getDefaultInstance()))
                  .setSchemaDescriptor(new messagesMethodDescriptorSupplier("send"))
                  .build();
          }
        }
     }
     return getSendMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static messagesStub newStub(io.grpc.Channel channel) {
    return new messagesStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static messagesBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new messagesBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static messagesFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new messagesFutureStub(channel);
  }

  /**
   */
  public static abstract class messagesImplBase implements io.grpc.BindableService {

    /**
     */
    public void messageIn(com.cloudMessaging.cloud.User.Empty request,
        io.grpc.stub.StreamObserver<com.cloudMessaging.cloud.User.Text> responseObserver) {
      asyncUnimplementedUnaryCall(getMessageInMethod(), responseObserver);
    }

    /**
     */
    public void send(com.cloudMessaging.cloud.User.Text request,
        io.grpc.stub.StreamObserver<com.cloudMessaging.cloud.User.delivery> responseObserver) {
      asyncUnimplementedUnaryCall(getSendMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMessageInMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.cloudMessaging.cloud.User.Empty,
                com.cloudMessaging.cloud.User.Text>(
                  this, METHODID_MESSAGE_IN)))
          .addMethod(
            getSendMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.cloudMessaging.cloud.User.Text,
                com.cloudMessaging.cloud.User.delivery>(
                  this, METHODID_SEND)))
          .build();
    }
  }

  /**
   */
  public static final class messagesStub extends io.grpc.stub.AbstractStub<messagesStub> {
    private messagesStub(io.grpc.Channel channel) {
      super(channel);
    }

    private messagesStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected messagesStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new messagesStub(channel, callOptions);
    }

    /**
     */
    public void messageIn(com.cloudMessaging.cloud.User.Empty request,
        io.grpc.stub.StreamObserver<com.cloudMessaging.cloud.User.Text> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMessageInMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void send(com.cloudMessaging.cloud.User.Text request,
        io.grpc.stub.StreamObserver<com.cloudMessaging.cloud.User.delivery> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class messagesBlockingStub extends io.grpc.stub.AbstractStub<messagesBlockingStub> {
    private messagesBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private messagesBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected messagesBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new messagesBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.cloudMessaging.cloud.User.Text messageIn(com.cloudMessaging.cloud.User.Empty request) {
      return blockingUnaryCall(
          getChannel(), getMessageInMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cloudMessaging.cloud.User.delivery send(com.cloudMessaging.cloud.User.Text request) {
      return blockingUnaryCall(
          getChannel(), getSendMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class messagesFutureStub extends io.grpc.stub.AbstractStub<messagesFutureStub> {
    private messagesFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private messagesFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected messagesFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new messagesFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cloudMessaging.cloud.User.Text> messageIn(
        com.cloudMessaging.cloud.User.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getMessageInMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cloudMessaging.cloud.User.delivery> send(
        com.cloudMessaging.cloud.User.Text request) {
      return futureUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_MESSAGE_IN = 0;
  private static final int METHODID_SEND = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final messagesImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(messagesImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MESSAGE_IN:
          serviceImpl.messageIn((com.cloudMessaging.cloud.User.Empty) request,
              (io.grpc.stub.StreamObserver<com.cloudMessaging.cloud.User.Text>) responseObserver);
          break;
        case METHODID_SEND:
          serviceImpl.send((com.cloudMessaging.cloud.User.Text) request,
              (io.grpc.stub.StreamObserver<com.cloudMessaging.cloud.User.delivery>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class messagesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    messagesBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cloudMessaging.cloud.User.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("messages");
    }
  }

  private static final class messagesFileDescriptorSupplier
      extends messagesBaseDescriptorSupplier {
    messagesFileDescriptorSupplier() {}
  }

  private static final class messagesMethodDescriptorSupplier
      extends messagesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    messagesMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (messagesGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new messagesFileDescriptorSupplier())
              .addMethod(getMessageInMethod())
              .addMethod(getSendMethod())
              .build();
        }
      }
    }
    return result;
  }
}
