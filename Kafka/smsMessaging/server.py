from concurrent import futures
import grpc, time, threading, smsmessage_pb2, smsmessage_pb2_grpc, os, logging, sms


class Listener(smsmessage_pb2_grpc.SMSMessagingServicer):

    def send_message(self, request, context):
        response = smsmessage_pb2.SendReponse()
        response.value = sms.send_message(request.client_number, request.message)
        return response

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4))
    smsmessage_pb2_grpc.add_SMSMessagingServicer_to_server(Listener(), server)
    server.add_insecure_port("[::]:50051")
    server.start()
    try:
        while True:
            print(time.time())
            #time.sleep(10)
    except KeyboardInterrupt:
        print("KeyboardInterrupt")
        server.stop(0)

if __name__ == "__main__":
    serve()