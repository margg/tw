package zad1;

import zad1.activeObject.Scheduler;
import zad1.activeObject.Servant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Małgorzata Salawa
 */
public class Main {

    public static void main(String[] args) {

        int BUFFER_SIZE = 100;

        int CONSUMERS_COUNT = 10;
        int PRODUCERS_COUNT = 10;

        ExecutorService service = Executors.newFixedThreadPool(PRODUCERS_COUNT + CONSUMERS_COUNT);

        Scheduler scheduler = new Scheduler(BUFFER_SIZE);
        Servant servant = new Servant(BUFFER_SIZE);
        BufferProxy<Integer> buffer = new BufferProxy<Integer>(BUFFER_SIZE, scheduler, servant);

        scheduler.run();

        for (int i = 0; i < PRODUCERS_COUNT; i++) {
            service.submit(new Producer(buffer));
        }
        for (int i = 0; i < CONSUMERS_COUNT; i++) {
            service.submit(new Consumer(buffer));
        }

        service.shutdown();
    }


}
