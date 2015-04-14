package zad2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Małgorzata Salawa
 */
public class zad2 {

    public static void main(String[] args) {

        int BUFFER_SIZE = 1000;

        int CONSUMERS_COUNT = 55;
        int PRODUCERS_COUNT = 100;

        ExecutorService service = Executors.newFixedThreadPool(PRODUCERS_COUNT + CONSUMERS_COUNT);

        RandomBuffer buffer = new RandomBuffer(BUFFER_SIZE/2);

        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            service.submit(new Producer(buffer));
        }
        for (int i = 0; i < CONSUMERS_COUNT; i++) {
            service.submit(new Consumer(buffer));
        }

        service.shutdown();



    }



}
