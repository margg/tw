package zad1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Małgorzata Salawa
 */
public class Producer extends Thread {
    final BufferProxy<Integer> buffer;

    public Producer(BufferProxy<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while (true) {
            try {
                int t = (int) (Math.random() * 1000);
                System.out.println("Producing: " + t);
                buffer.put(t);

                sleep((long) (2000 * Math.random()));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
