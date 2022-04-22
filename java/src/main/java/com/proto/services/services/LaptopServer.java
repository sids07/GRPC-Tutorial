package com.proto.services.services;

import io.grpc.ServerBuilder;

import java.util.logging.Logger;

public class LaptopServer {
    private static Logger logger = Logger.getLogger(LaptopServer.class.getName());

    public LaptopServer(int port, LaptopStore store){

    }
    public LaptopServer(ServerBuilder serverbuilder, int port, LaptopStore store){
//        will be useful when writing unit code
    }
}
