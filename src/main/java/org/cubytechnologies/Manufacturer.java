package org.cubytechnologies;

import java.util.ArrayList;

public class Manufacturer implements Runnable {

    private Buffer buffer;                        //буфер
    private Integer serialNumberItem;             //серийный номер
    private int speedProduce;                     //скорость производства в 1 сек.
    private ArrayList<Consumer> consumersList;    //список потребителей
    private ArrayList<Integer> manufacturerItems; //список произведенных серийных номеров

    public Manufacturer(Buffer buffer, int speedProduce) {
        this.buffer = buffer;
        this.serialNumberItem = 0;
        this.speedProduce = speedProduce;
        this.consumersList = new ArrayList<>();
        this.manufacturerItems = new ArrayList<>();
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public Integer getSerialNumberItem() {
        return serialNumberItem;
    }

    public void setSerialNumberItem(Integer serialNumberItem) {
        this.serialNumberItem = serialNumberItem;
    }

    public int getSpeedProduce() {
        return speedProduce;
    }

    public void setSpeedProduce(int speedProduce) {
        this.speedProduce = speedProduce;
    }

    public ArrayList<Consumer> getConsumersList() {
        return consumersList;
    }

    public void setConsumersList(ArrayList<Consumer> consumersList) {
        this.consumersList = consumersList;
    }

    public ArrayList<Integer> getManufacturerItems() {
        return manufacturerItems;
    }

    public void setManufacturerItems(ArrayList<Integer> manufacturerItems) {
        this.manufacturerItems = manufacturerItems;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            setSerialNumberItem(getSerialNumberItem());
            setSerialNumberItem(buffer.addItemToBuffer(manufacturerItems, serialNumberItem, speedProduce, consumersList.get(0).getCountNeed()));
            try {
                Thread.sleep(1000);//остановка на 1 сек. так как speedProduce это кол-во изготовления в 1 сек.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

