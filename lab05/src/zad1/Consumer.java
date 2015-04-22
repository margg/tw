package zad1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Małgorzata Salawa
 */
public class Consumer extends Thread {
    final BufferProxy<Integer> buffer;

    public Consumer(BufferProxy<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while (true) {
            try {
//                System.out.println("Taking... ");
                Future<Integer> future = buffer.take();

               /* while(!future.isDone()) {
                    sleep(100);
                }*/
                int result = future.get();
                System.err.println("Result: " + result);

                sleep((long) (2000 * Math.random()));

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}
