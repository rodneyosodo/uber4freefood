import os, smsmessage_pb2, smsmessage_pb2_grpc, time, grpc

def run():
    with grpc.insecure_channel("localhost:50051") as channel:
        stub = smsmessage_pb2_grpc.SMSMessagingStub(channel)
        while True:
            try:
                request = smsmessage_pb2.SendSmsRequest(client_number='+254720136609', message="Hello")
                response = stub.send_req(request)
                print("response.value")
            except KeyboardInterrupt:
                print("KeyboardInterrupt")
                channel.unsubscribe(close)
                exit()

def close(channel):
    channel.close()

if __name__ == "__main__":
    run()