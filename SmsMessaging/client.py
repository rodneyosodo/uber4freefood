import sys, grpc
import smsmessage_pb2_grpc as sms_service
import message_types_pb2 as sms_messages


def run():
    channel = grpc.insecure_channel('localhost:50051')
    try:
        grpc.channel_ready_future(channel).result(timeout=10)
    except grpc.FutureTimeoutError:
        sys.exit('Error connecting to server')
    else:
        stub = sms_service.SMSMessagingStub(channel)
        metadata = [('ip', '127.0.0.1')]
        response = stub.SendMessage(
            sms_messages.SendSmsRequest(client_number='+254720136609', message="Hello"),
            metadata=metadata,
        )
        if response:
            print("Message sent")

if __name__ == '__main__':
    run()
