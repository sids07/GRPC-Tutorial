package com.proto.services.serializer;

import com.proto.services.sample.Generator;
import com.protos.learn.sid.TestLaptopMessage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SerializerTest {

    @Test
    public void writeBinaryFile() throws IOException {
        String filename = "laptop.bin";
        TestLaptopMessage.Laptop laptop1 = new Generator().NewLaptop();

        Serializer serializer = new Serializer();
        serializer.writeBinaryFile(laptop1,filename);

        TestLaptopMessage.Laptop laptop2 = serializer.readBinaryFile(filename);
        Assert.assertEquals(laptop1,laptop2);
    }
}