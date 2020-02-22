from concurrent import futures
import time, sms, grpc
import smsmessage_pb2_grpc as sms_service
import message_types_pb2 as sms_messages

_ONE_DAY_IN_SECONDS = 60 * 60 * 24


class SmsMessageService(sms_service.SMSMessagingServicer):
    def SendMessage(self, request, context):
        metadata = dict(context.invocation_metadata())
        print(metadata)
        m_id = sms.send_message(message=request.message, number=request.client_number)
        message = sms_messages.TextMessage(text=request.message, message_id=m_id)
        print(message)
        return sms_messages.SendSmsReponse(message=message)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    sms_service.add_SMSMessagingServicer_to_server(SmsMessageService(), server)
    server.add_insecure_port('127.0.0.1:50051')
    server.start()
    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)

if __name__ == '__main__':
    serve()
