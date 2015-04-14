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
    private List<Integer> buffer = new ArrayList<Integer>(BUFFER_SIZE);

    private List<Lock> clientsLocks = new ArrayList<Lock>(CLIENTS_COUNT);
    private List<Condition> conditions = new ArrayList<Condition>(CLIENTS_COUNT);
    private List<Integer> clientsPositions = new ArrayList<Integer>(CLIENTS_COUNT);

    public Buffer(int bufferSize, int sumOfClients) {
        this.BUFFER_SIZE = bufferSize;
        this.CLIENTS_COUNT = sumOfClients;
    }


    public void blockUntilNextAvailable(int clientId) throws InterruptedException {
        Condition previousCondition = conditions.get(clientId - 1);
        Lock previousLock = clientsLocks.get(clientId - 1);

        previousLock.lock();
        try {
            int desiredIndex = clientsPositions.get(clientId) + 1;
            if (desiredIndex == BUFFER_SIZE) {
                desiredIndex = 0;
            }

            int previousClientIndex = clientsPositions.get(clientId - 1);

            while (desiredIndex == previousClientIndex) {
                previousCondition.await();
                previousClientIndex = clientsPositions.get(clientId - 1);
            }
        } finally {
            previousLock.unlock();
        }
    }

    public void startProcessingNext(int clientId) {
        Lock ourLock = clientsLocks.get(clientId);

        ourLock.lock();
        int current = clientsPositions.get(clientId);
        if(current == BUFFER_SIZE - 1) {
            current = -1;
        }
        clientsPositions.set(clientId, current + 1);
        ourLock.unlock();
    }

    public void processCurrentElement(int clientId) {
        Integer currentPosition = clientsPositions.get(clientId);
        Integer currentValue = buffer.get(currentPosition);
        if(currentValue == CLIENTS_COUNT-1) {
            System.out.println("Reached end! Consuming...");
            currentValue = -2;
        }
        buffer.set(currentPosition, currentValue + 1);
    }

    public void finalizeProcessing(int clientId) {
        Condition ourCondition = conditions.get(clientId);
        Lock ourLock = clientsLocks.get(clientId);

        ourLock.lock();
        ourCondition.signalAll();
        ourLock.unlock();
    }

    public void setUp() {
        for (int i = 0; i < buffer.size(); i++) {
            buffer.set(i, -1);
        }
        for (int i = 0; i < clientsLocks.size(); i++) {
            Lock lock = new ReentrantLock();
            clientsLocks.set(i, lock);
            conditions.set(i, lock.newCondition());
            clientsPositions.set(i, -1);
        }
    }
}
