from concurrent import futures
import time
import requests
import grpc
import json
import delivery_pb2_grpc as delivery_service
import delivery_pb2 as delivery_messages

_ONE_DAY_IN_SECONDS = 60 * 60 * 24


class DeliveryService(delivery_service.UsersServicer):
    def CancelDelivery(self, request, context):
        metadata = dict(context.invocation_metadata())
        print(metadata)
        values = {
            "command": "cancel",
            "data":
            {
                "api_key": "aOYE0BD3rz03QKPXUx4R",
                "api_username": "qualis",
                "order_no": "AN82TT944-33W"
            },
            "request_token_id": "request_token_id"
        }
        headers = {
            'Content-Type': 'application/json'
        }
        url = 'https://apitest.sendyit.com/v1/#cancel'
        request = requests.post(url, data=json.dumps(values), headers=headers)
        response_body = request.json()
        status = str(response_body.get('status'))
        data = str(response_body['data'])
        request_token_id = str(response_body['request_token_id'])
        return delivery_messages.CancelDeliveryResponse(status=status, data=data, request_token_id = request_token_id)

    # def CreateUser(self, request, context):
    #     metadata = dict(context.invocation_metadata())
    #     print(metadata)
    #     user = users_messages.User(username=request.username, user_id=1)
    #     return users_messages.CreateUserResult(user=user)

    # def GetUsers(self, request, context):
    #     for user in request.user:
    #         user = users_messages.User(
    #             username=user.username, user_id=user.user_id
    #         )
    #         yield users_messages.GetUsersResult(user=user)


def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    delivery_service.add_UsersServicer_to_server(DeliveryService(), server)
    server.add_insecure_port('0.0.0.0:50051')
    server.start()
    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':
    serve()
