package zad2;

/**
 * @author Małgorzata Salawa
 */
public class Consumer extends Thread {
    final RandomBuffer buffer;

    public Consumer(RandomBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int numberOfElements = (int) (buffer.getMaxNumOfElements() * Math.random()) + 1;

                buffer.takeElements(numberOfElements);

                sleep((long) (2000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}