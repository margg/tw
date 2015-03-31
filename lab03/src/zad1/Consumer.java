package zad1;

public class Consumer implements Runnable {
    private BoundedBuffer buffer;

    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 0; i < 100; i++) {
            try {
                String message = (String) buffer.take();
                System.out.println("Consumer " + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
