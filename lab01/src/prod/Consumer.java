package prod;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 0; i < producers.N; i++) {
            try {
                String message = buffer.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
