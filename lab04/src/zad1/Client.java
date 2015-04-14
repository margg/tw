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
                System.out.println("Client " + clientId + " waiting until next available");
                buffer.blockUntilNextAvailable(clientId);
                System.out.println("Client " + clientId + " starting processing");
                buffer.startProcessingNext(clientId);

                System.out.println("Client " + clientId + " processing");
                buffer.processCurrentElement(clientId);
                sleep(1000);
                System.out.println("Client " + clientId + " finalizing processing");
                buffer.finalizeProcessing(clientId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
