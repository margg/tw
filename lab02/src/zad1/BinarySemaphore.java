package zad1;

/**
 * @author Małgorzata Salawa
 */
public class BinarySemaphore implements Semaphore {

    private volatile int state = 1;

    public BinarySemaphore() {}

    public BinarySemaphore(int state) {
        this.state = state;
    }

    @Override
    public synchronized void take() {
        while (state == 0) {
            try {

                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        state = 0;
    }

    @Override
    public synchronized void give() {
        state = 1;
        notifyAll();
    }

}
