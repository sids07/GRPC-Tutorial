package com.proto.services.services;

import com.protos.learn.sid.CreateLaptopRequest;
import com.protos.learn.sid.CreateLaptopResponse;
import com.protos.learn.sid.LaptopServiceGrpc;
import com.protos.learn.sid.TestLaptopMessage;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.UUID;
import java.util.logging.Logger;

public class LaptopService extends LaptopServiceGrpc.LaptopServiceImplBase {
    private static final Logger logger = Logger.getLogger(LaptopService.class.getName());

    public LaptopStore store;

    public LaptopService(LaptopStore store){
        this.store  = store;
    }
    @Override
    public void createLaptop(CreateLaptopRequest request, StreamObserver<CreateLaptopResponse> responseObserver) {
        TestLaptopMessage.Laptop laptop = request.getLaptop();
        String id = laptop.getId();

        logger.info("GOt a createLaptopRequest with id: " + id);

        UUID uuid;
        if (id.isEmpty()){
            uuid = UUID.randomUUID();
        }
        else{
            try {
                uuid = UUID.fromString(id);
            }
            catch(IllegalArgumentException e){
                responseObserver.onError(
                        Status.INVALID_ARGUMENT
                                .withDescription(e.getMessage())
                                .asRuntimeException()
                );
                return;
            }
        }
        TestLaptopMessage.Laptop other = laptop
                .toBuilder()
                .setId(uuid.toString())
                .build();

        try {
            store.Save(other);
        }
        catch (AlreadyExistException e){
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        catch (Exception e){
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }
        CreateLaptopResponse response = CreateLaptopResponse
                .newBuilder()
                .setId(other.getId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("Laptop saved with id: " + other.getId());
    }
}
