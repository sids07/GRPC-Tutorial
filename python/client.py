import grpc
import calculator_pb2
import calculator_pb2_grpc

#step 1: Create a channel
channel = grpc.insecure_channel('0.0.0.0:50051')

#step 2: Create a Stub
stub = calculator_pb2_grpc.CalculatorStub(channel)

#step 3: call API and get response
number = calculator_pb2.Number(value=16)
response = stub.SquareRoot(number)
print(response)
print(response.value)