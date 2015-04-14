package zad1;

import static java.lang.Thread.sleep;

/**
 * @author Małgorzata Salawa
 */
public class Client implements Runnable {
    private Buffer buffer;
    private final int clientId;

    public Client(Buffer buffer, int clientId) {
        this.buffer = buffer;
        this.clientId = clientId;
    }

    public void run() {

        while (true) {
            try {
                System.out.println("");
                buffer.blockUntilNextAvailable(clientId);
                buffer.startProcessingNext(clientId);

                buffer.processCurrentElement(clientId);
                sleep(1000);
                buffer.finalizeProcessing(clientId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
