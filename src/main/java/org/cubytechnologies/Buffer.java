package org.cubytechnologies;

import java.util.ArrayList;
import java.util.Iterator;

public class Buffer {

    private int sizeBuffer;                        //размер буфера
    private final ArrayList<Integer> bufferItems;  //список серийных номеров буфера для обмена между Manufacturer и Consumer

    public Buffer(int sizeBuffer) {
        this.sizeBuffer = sizeBuffer;
        bufferItems = new ArrayList<>();
    }

    public int getSizeBuffer() {
        return sizeBuffer;
    }

    public void setSizeBuffer(int sizeBuffer) {
        this.sizeBuffer = sizeBuffer;
    }

    public ArrayList<Integer> getBufferItems() {
        return bufferItems;
    }

    //синхронизированный метод получения серийных номеров из буфера потребителю
    public synchronized void getItemFromBuffer(ArrayList<Integer> consumerItems, int countNeed) {

        //если потребителю нужно будет кол-во больше чем буфер, при равниваем потребление к размеру буфера
        if (countNeed > sizeBuffer) {
            countNeed = sizeBuffer;
        }

        //будем ждать пока не будет необходимого кол-ва в буфере
        while (countNeed > bufferItems.size() ) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Поток прервали :(");
                Thread.currentThread().interrupt();
            }
        }

        //получение серийных номеров из буфера
        //забериаем то кол-во, сколько необходимо
        System.out.println("Потребление...");
        Iterator<Integer> iterator = bufferItems.iterator();
        while (iterator.hasNext() && countNeed != 0) {
            consumerItems.add(bufferItems.get(0));
            System.out.println("Потребитель забрал товар с серийным номером " + bufferItems.get(0));
            bufferItems.remove(0);
            countNeed--;
            try {
                Thread.sleep(500); //пауза для отслеживания получения серийного номера
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        notify();
    }

    //синхронизированный метод добавления серийных номеров в буфер от производителя
    public synchronized Integer addItemToBuffer(ArrayList<Integer> manufacturerItems, Integer serialNumberItem, int speedProduce, int countNeed) {
        //будем ждать пока буфер полон или кол-во серийных номеров в буфере больше или равно необходимого потребителю
        while (bufferItems.size() == sizeBuffer || bufferItems.size() >= countNeed) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Поток прервали :(");
                Thread.currentThread().interrupt();
            }
        }

        //добавляем серийные номера в буфер в зависимости от скорости производства
        System.out.println("Производство...");
        while (speedProduce != 0 && sizeBuffer != bufferItems.size() ) {
            bufferItems.add(++serialNumberItem);
            manufacturerItems.add(serialNumberItem);
            System.out.println("Произодитель добавил товар с серийным номером " + bufferItems.get(bufferItems.size()-1));
            speedProduce--;
        }
        notify();
        return serialNumberItem;
    }
}
