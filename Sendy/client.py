import sys

import grpc

import delivery_pb2_grpc as delivery_service
import delivery_pb2 as delivery_messages

def run():
    channel = grpc.insecure_channel('localhost:50051')
    try:
        grpc.channel_ready_future(channel).result(timeout=10)
    except grpc.FutureTimeoutError:
        sys.exit('Error connecting to server')
    else:
        stub = delivery_service.UsersStub(channel)
        metadata = [('ip', '127.0.0.1')]
        response = stub.CancelDelivery(
            delivery_messages.CancelDeliveryRequest(order_no="AN82TT944-33W",request_token_id="request_token_id"),
            metadata = metadata,
        )
        if response:
            print("Delivery cancelled:", response)


if __name__ == '__main__':
    run()
