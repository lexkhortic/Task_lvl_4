package org.cubytechnologies;

import java.util.ArrayList;

public class Consumer implements Runnable {

    private Buffer buffer;                    //буфер
    private int countNeed;                    //необходимое кол-во потребителю
    private ArrayList<Integer> consumerItems; //список необходиомого кол-ва потребителю(серийные номера)

    public Consumer(Buffer buffer, int countNeed) {
        this.buffer = buffer;
        this.countNeed = countNeed;
        this.consumerItems = new ArrayList<>();
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public int getCountNeed() {
        return countNeed;
    }

    public void setCountNeed(int countNeed) {
        this.countNeed = countNeed;
    }

    public ArrayList<Integer> getConsumerItems() {
        return consumerItems;
    }

    public void setConsumerItems(ArrayList<Integer> consumerItems) {
        this.consumerItems = consumerItems;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            buffer.getItemFromBuffer(consumerItems, countNeed);
        }
    }
}
