package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Małgorzata Salawa
 */
public class Buffer {


    private int BUFFER_SIZE;
    private int CLIENTS_COUNT;
    private List<Integer> buffer;

    private List<Lock> clientsLocks;
    private List<Condition> conditions;
    private List<Integer> clientsPositions;

    public Buffer(int bufferSize, int sumOfClients) {
        this.BUFFER_SIZE = bufferSize;
        this.CLIENTS_COUNT = sumOfClients;
        buffer = new ArrayList<Integer>(BUFFER_SIZE);
        clientsLocks = new ArrayList<Lock>(CLIENTS_COUNT);
        conditions = new ArrayList<Condition>(CLIENTS_COUNT);
        clientsPositions = new ArrayList<Integer>(CLIENTS_COUNT);
    }


    public void blockUntilNextAvailable(int clientId) throws InterruptedException {
        int idOfClientToWaitFor = (clientId == 0) ? CLIENTS_COUNT-1 : clientId - 1;

        Condition previousCondition = conditions.get(idOfClientToWaitFor);
        Lock previousLock = clientsLocks.get(idOfClientToWaitFor);

        previousLock.lock();
        try {
            int desiredIndex = clientsPositions.get(clientId) + 1;
            if (desiredIndex == BUFFER_SIZE) {
                desiredIndex = 0;
            }

            int previousClientIndex = clientsPositions.get(idOfClientToWaitFor);

            while (desiredIndex == previousClientIndex) {

//                System.out.println("Client " + clientId + " waiting for previous client at index " + previousClientIndex);

                previousCondition.await();
                previousClientIndex = clientsPositions.get(idOfClientToWaitFor);
            }

            System.out.println("Client " + clientId + " has space to process.");

        } finally {
            previousLock.unlock();
        }
    }

    public void startProcessingNext(int clientId) {
        Lock ourLock = clientsLocks.get(clientId);

        ourLock.lock();
        int current = clientsPositions.get(clientId);
        if (current == BUFFER_SIZE - 1) {
            current = -1;
        }

//        System.out.println("Client " + clientId + " position: " + current + ". Moving on.");

        clientsPositions.set(clientId, current + 1);
        ourLock.unlock();
    }

    public void processCurrentElement(int clientId) {
        Integer currentPosition = clientsPositions.get(clientId);
        Integer currentValue = buffer.get(currentPosition);
        if (currentValue == CLIENTS_COUNT - 2) {
            System.out.println("Reached end! Consuming...");
            currentValue = -2;
        }

        System.out.println("Client " + clientId + " is processing element " + currentValue + " at index " + currentPosition);

        buffer.set(currentPosition, currentValue + 1);
    }

    public void finalizeProcessing(int clientId) {
        Condition ourCondition = conditions.get(clientId);
        Lock ourLock = clientsLocks.get(clientId);

        ourLock.lock();

        System.out.println("Client " + clientId + " signalling work done.");

        ourCondition.signal();
        ourLock.unlock();
    }

    public void setUp() {
        for (int i = 0; i < BUFFER_SIZE; i++) {
            buffer.add(i, -1);
        }
        for (int i = 0; i < CLIENTS_COUNT; i++) {
            Lock lock = new ReentrantLock();
            clientsLocks.add(i, lock);
            conditions.add(i, lock.newCondition());
            clientsPositions.add(i, -1);
        }
    }
}
