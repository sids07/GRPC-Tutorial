package com.proto.services.serializer;


import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import com.protos.learn.sid.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Random;

public class Serializer {
    public void writeBinaryFile(TestLaptopMessage.Laptop laptop, String filename) throws IOException{
        FileOutputStream outputStream = new FileOutputStream(filename);
        laptop.writeTo(outputStream);
        outputStream.close();
    }
    public TestLaptopMessage.Laptop readBinaryFile(String filename) throws IOException{
        FileInputStream inStream = new FileInputStream(filename);
        TestLaptopMessage.Laptop laptop  = TestLaptopMessage.Laptop.parseFrom(inStream);
        inStream.close();
        return laptop;
    }
    public void WriteJSONFile(TestLaptopMessage.Laptop laptop , String filename) throws IOException{
        JsonFormat.Printer printer = JsonFormat.printer()
                .includingDefaultValueFields()
                .preservingProtoFieldNames();

        String jsonString = printer.print(laptop);
        System.out.println("A");
        System.out.println(jsonString);
        System.out.println("B");
        System.out.println(jsonString.getBytes());
        FileOutputStream outputStream = new FileOutputStream(filename);
        outputStream.write(jsonString.getBytes());
        outputStream.close();
    }

    public static void main(String[] args) throws IOException {
        Serializer serializer = new Serializer();
        TestLaptopMessage.Laptop laptop = serializer.readBinaryFile("laptop.bin");
        serializer.WriteJSONFile(laptop,"laptop.json");
    }
 }

