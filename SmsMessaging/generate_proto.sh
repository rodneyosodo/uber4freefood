python3 -m grpc_tools.protoc -I./proto --python_out=. --grpc_python_out=. ./proto/smsmessage.proto
python3 -m grpc_tools.protoc -I./proto --python_out=. --grpc_python_out=. ./proto/message_types.proto
