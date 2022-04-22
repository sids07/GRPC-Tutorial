package com.proto.services.services;

import com.protos.learn.sid.TestLaptopMessage;

public interface LaptopStore {
    void Save(TestLaptopMessage.Laptop laptop) throws Exception;
    // For learning i have used same Laptop object as service but we should consider using separate object model here to decouple between data transfer and data storage layers.
    TestLaptopMessage.Laptop Find(String id);
}
