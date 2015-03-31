package zad1;

public class Producer implements Runnable {
    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 0; i < 100; i++) {
            try {
                buffer.put("message " + i);
                System.out.println("\tProducer message " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}