package com.proto.services.sample;

import com.google.protobuf.Timestamp;
import com.protos.learn.sid.*;

import java.time.Instant;
import java.util.Random;

public class Generator {
    private Random rand;

    public Generator(){
        rand = new Random();
    }
    public TestKeyboardMessage.Keyboard NewKeyboard(){
        return TestKeyboardMessage.Keyboard
                .newBuilder()
                .setLayout(randomKeyboardLayout())
                .setBacklit(rand.nextBoolean())
                .build();
    }

    public TestKeyboardMessage.Keyboard.Layout randomKeyboardLayout(){
        switch (rand.nextInt(3)){
            case 1:
                return TestKeyboardMessage.Keyboard.Layout.QWERTY;
            case 2:
                return TestKeyboardMessage.Keyboard.Layout.QWERTZ;
            default:
                return TestKeyboardMessage.Keyboard.Layout.AZERTY;
        }
    }

    public TestProcessorMessage.CPU NewCPU(){
        String brand = randomSringFromSet("INTEL","DELL");
        String name = randomName(brand);
        int number_cores = randomNumber(2,8);
        int number_threads = randomNumber(number_cores,12);
        float min_ghz = (float) randomDoubleNumber(2.5,3.5);
        double max_ghz = randomDoubleNumber(min_ghz,12.5);

        return TestProcessorMessage.CPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setNumberCores(number_cores)
                .setNumberThreads(number_threads)
                .setMinGhz(min_ghz)
                .setMaxGhz(max_ghz)
                .build();
    }

    public TestProcessorMessage.GPU NewGPU(){
        String brand = randomSringFromSet("Nvidia","Raezon");
        String name = randomName(brand);
        Double min_ghz = randomDoubleNumber(1.0,1.5);
        Double max_ghz = randomDoubleNumber(min_ghz,2.0);
        TestMemoryMessage.Memory memory = TestMemoryMessage.Memory
                .newBuilder()
                .setValue(randomNumber(2,6))
                .setUnit(TestMemoryMessage.Memory.Unit.GIGABYTE)
                .build();

        return TestProcessorMessage.GPU
                .newBuilder()
                .setBrand(brand)
                .setName(name)
                .setMinGhz(min_ghz)
                .setMaxGhz(max_ghz)
                .setMemory(memory)
                .build();
    }

    public TestMemoryMessage.Memory NewMemory(){
        int value = randomNumber(2,8);
        TestMemoryMessage.Memory.Unit unit = TestMemoryMessage.Memory.Unit.GIGABYTE;

        return TestMemoryMessage.Memory
                .newBuilder()
                .setValue(value)
                .setUnit(unit)
                .build();
    }

    public TestStorageMessage.Storage NewSSD(){
        TestStorageMessage.Storage.Driver driver = TestStorageMessage.Storage.Driver.SDD;
        TestMemoryMessage.Memory memory = TestMemoryMessage.Memory.newBuilder()
                .setValue(randomNumber(3,6))
                .setUnit(TestMemoryMessage.Memory.Unit.MEGABYTE)
                .build();

        return TestStorageMessage.Storage.newBuilder()
                .setDriver(driver)
                .setMemory(memory)
                .build();
    }

    public TestStorageMessage.Storage NewHDD(){
        TestStorageMessage.Storage.Driver driver = TestStorageMessage.Storage.Driver.HDD;
        TestMemoryMessage.Memory memory = TestMemoryMessage.Memory.newBuilder()
                .setValue(randomNumber(3,6))
                .setUnit(TestMemoryMessage.Memory.Unit.TERABYTE)
                .build();

        return TestStorageMessage.Storage.newBuilder()
                .setDriver(driver)
                .setMemory(memory)
                .build();
    }

    public TestScreenMessage.Screen NewScreen(){
        int width = randomNumber(1000,4500);
        int height = width * 16/9;

        TestScreenMessage.Screen.Resolution resolution = TestScreenMessage.Screen.Resolution.newBuilder()
                .setHeight(height)
                .setWidth(width)
                .build();

        return TestScreenMessage.Screen.newBuilder()
                .setSizeInch((float) randomDoubleNumber(1.5,3))
                .setResolution(resolution)
                .setPanel(TestScreenMessage.Screen.Panel.IPS)
                .setMultitouch(true)
                .build();
    }

    public TestLaptopMessage.Laptop NewLaptop(){
        String brand = randomSringFromSet("Acer","HP");
        String name = randomName(brand);
        double weight_kg = randomDoubleNumber(1.0,3.0);
        double price_usd = randomDoubleNumber(1500,3500);
        int release_year = randomNumber(2015,2022);

        return TestLaptopMessage.Laptop.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setCpu(NewCPU())
                .setRam(NewMemory())
                .addGpus(NewGPU())
                .addStorages(NewHDD())
                .addStorages(NewSSD())
                .setScreen(NewScreen())
                .setKeyboard(NewKeyboard())
                .setWeightKg(weight_kg)
                .setPriceUsd(price_usd)
                .setReleaseYear(release_year)
                .setUpdatedAt(timestampNow())
                .build();
    }

    public Timestamp timestampNow(){
        Instant now = Instant.now();

        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }
    public double randomDoubleNumber(double min, double max){
        return min + rand.nextDouble()*(max-min);
    }

    public int randomNumber(int min, int max){
        return min+rand.nextInt(max-min+1);
    }

    public String randomName(String brand){
        if(brand=="INTEL"){
            return randomSringFromSet(
                    "INTEL1",
                    "INTEL2",
                    "INTEL3");
        }
        else if(brand=="Nvidia"){
            return randomSringFromSet(
                    "RTX1089",
                    "RTX2089");
        }
        else if(brand=="Raezon"){
            return randomSringFromSet(
                    "Raezon1",
                    "Raezon2");
        }
        else if(brand=="Acer"){
            return randomSringFromSet(
                    "ACER1",
                    "ACER2");
        }
        else if(brand=="HP"){
            return randomSringFromSet(
                    "HP1",
                    "HP2");
        }
        else{
            return randomSringFromSet(
                    "DELL1",
                    "DELL2",
                    "DELL3");
        }
    }

    public String randomSringFromSet(String ... str){
        int n = str.length;

        if(n==0){
            return "";
        }
        return str[rand.nextInt(n)];
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        TestLaptopMessage.Laptop laptop = generator.NewLaptop();
        System.out.println(laptop);
    }
}
