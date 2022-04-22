try:
    import grpc
    from concurrent import futures
    import time
    import calculator
    import calculator_pb2
    import calculator_pb2_grpc

except Exception as e:
    print("Error Loading Module ")

_ONE_DAY_IN_SECONDS = 60*60*24

class CalculatorServicer(calculator_pb2_grpc.CalculatorServicer):

    '''
    Here once we feed our protos to the grpc_tools several files are created(calculator_pb2.py and calculator_pb2_grpc.py).
    So, when we defined our rpc servie in protos one of the file that was created via grpc(calculator_pb2_grpc.py) will have the servicer created automatically based on protos.
    Now we inherit that servicer from calculator_pb2_grpc.py and alsp SquareRoot method (as we have mentioned it in out protos) and then write our api code
    '''

    def SquareRoot(self, request, context):
        response = calculator_pb2.Number()
        response.value = calculator.square_root(request.value)
        return response

def run():

    '''
    Now that we have created our flow for api we must register it to our grpc server.
    So, we call the server and add our method using add_CalculatorServicer_to_server from calculator_pb2_grpc.py
    '''

    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    calculator_pb2_grpc.add_CalculatorServicer_to_server(CalculatorServicer(),server)
    print("Starting server. Listening on port 50051.")
    server.add_insecure_port('[::]:50051')
    server.start()

    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)

if __name__ == "__main__":
    run()