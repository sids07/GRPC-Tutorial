package com.proto.services.services;

import com.protos.learn.sid.TestLaptopMessage;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryLaptopStore implements LaptopStore{
    private ConcurrentMap<String, TestLaptopMessage.Laptop> data;
    /*
        ConcurrentMap<K,V>
        Type Parameters:
            K - the type of keys maintained by this map
            V - the type of mapped values
     */

    public InMemoryLaptopStore(){
        data = new ConcurrentHashMap<>(0);
    }
    @Override
    public void Save(TestLaptopMessage.Laptop laptop) throws Exception {
        if (data.containsKey(laptop.getId())){
            throw new AlreadyExistException("File already Exist");
        }

        TestLaptopMessage.Laptop other = laptop.toBuilder().build();
        data.put(other.getId(),other);
    }

    @Override
    public TestLaptopMessage.Laptop Find(String id) {
        if(!data.containsKey(id)){
            return null;
        }

        TestLaptopMessage.Laptop other = data.get(id).toBuilder().build();
        return other;
    }
}
