package prod;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for (int i = 0; i < producers.N; i++) {
            try {
                buffer.put("message " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}