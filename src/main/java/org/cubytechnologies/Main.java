package org.cubytechnologies;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(10);

        //кейс когда скорость производства меньше, чем нужно потребителю
//        Manufacturer manufacturer = new Manufacturer(buffer, 2);
//        Consumer consumer = new Consumer(buffer, 10);

        //кейс когда скорость производства больше, чем нужно потребителю
//        Manufacturer manufacturer = new Manufacturer(buffer, 7);
//        Consumer consumer = new Consumer(buffer, 2);

        //кейс когда скорость производства равна потребилению
//        Manufacturer manufacturer = new Manufacturer(buffer, 5);
//        Consumer consumer = new Consumer(buffer, 5);

        //кейс когда скорость производства равна буферу, а потребление меньше
//        Manufacturer manufacturer = new Manufacturer(buffer, 10);
//        Consumer consumer = new Consumer(buffer, 2);

        //кейс когда скорость потребление больше буфера, в этом случае будем приравнивать кол-во потребелния кол-ву буфера
        Manufacturer manufacturer = new Manufacturer(buffer, 10);
        Consumer consumer = new Consumer(buffer, 20);

        manufacturer.getConsumersList().add(consumer);
        Thread threadManufacturer = new Thread(manufacturer);
        Thread threadConsumer = new Thread(consumer);
        threadManufacturer.start();
        threadConsumer.start();

        try {
            Thread.sleep(25000);
            threadManufacturer.interrupt();
            threadConsumer.interrupt();

            Thread.sleep(1000);
            System.out.println("Остановка потоков...");
            System.out.print("Произведено: ");
            manufacturer.getManufacturerItems().forEach(el -> System.out.print(el + " "));
            System.out.println();
            System.out.print("Получено: ");
            consumer.getConsumerItems().forEach(el -> System.out.print(el + " "));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}