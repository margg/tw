package zad2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Małgorzata Salawa
 */
public class Producer extends Thread {
    final RandomBuffer buffer;

    public Producer(RandomBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {

        while (true) {
            int numberOfElements = 0;
            try {
                numberOfElements = (int) (buffer.getMaxNumOfElements() * Math.random()) + 1;
                List<Integer> productsArray = new ArrayList<Integer>(numberOfElements);
                for (int i = 0; i < numberOfElements; i++) {
                    productsArray.add((int) (10000 * Math.random()));
                }

                buffer.putElements(productsArray);
                sleep((long) (2000 * Math.random()));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}