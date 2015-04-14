package zad2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Małgorzata Salawa
 */
public class RandomBuffer {

    private final List<Integer> buffer;
    private int bufferElementsCount;

    private final Lock lock = new ReentrantLock();
    private final Condition firstProducer = lock.newCondition();
    private final Condition otherProducers = lock.newCondition();
    private final Condition firstConsumer = lock.newCondition();
    private final Condition otherConsumers = lock.newCondition();
    private boolean producerAlreadyWaiting = false;
    private boolean consumerAlreadyWaiting = false;


    public RandomBuffer(int maxNumOfElements) {
        bufferElementsCount = 0;
        buffer = new ArrayList<Integer>(2 * maxNumOfElements);
        for (int i = 0; i < 2 * maxNumOfElements; i++) {
            buffer.add(i, -1);
        }
    }

    public int getMaxNumOfElements() {
        return buffer.size() / 2;
    }

    public void putElements(List<Integer> productsArray) throws InterruptedException {

        lock.lock();
        try {
            if (producerAlreadyWaiting) {
                otherProducers.await();
            }

            producerAlreadyWaiting = true;
            int numOfProducts = productsArray.size();

            System.out.println("Producer " + Thread.currentThread().getId() + " waiting for \t" + numOfProducts + " free units");

            while (notEnoughSpaceInBuffer(numOfProducts)) {
                firstProducer.await();
            }

            bufferElementsCount += numOfProducts;
            for (int bufferIndex = 0, productsArrayIndex = 0; productsArrayIndex < numOfProducts; bufferIndex++) {
                if (buffer.get(bufferIndex) == -1) {
                    buffer.set(bufferIndex, productsArray.get(productsArrayIndex));
                    productsArrayIndex++;
                }
            }

            System.out.println("Producer " + Thread.currentThread().getId() + " produced   \t" + numOfProducts + " units");

            producerAlreadyWaiting = false;
            otherProducers.signal();
            firstConsumer.signal();
        } finally {
            lock.unlock();
        }
    }

    private boolean notEnoughSpaceInBuffer(int productsArraySize) {
        return (buffer.size() - bufferElementsCount) < productsArraySize;
    }

    public List<Integer> takeElements(int numberOfElements) throws InterruptedException {

        lock.lock();
        try {

            System.out.println("\tConsumer " + Thread.currentThread().getId() + " asking for \t" + numberOfElements + " units");

            if (consumerAlreadyWaiting) {
                otherConsumers.await();
            }
            consumerAlreadyWaiting = true;
            while (notEnoughUnitsInBuffer(numberOfElements)) {
                firstConsumer.await();
            }

            bufferElementsCount -= numberOfElements;
            List<Integer> returnValuesArray = new ArrayList<Integer>(numberOfElements);

            for (int bufferIndex = 0, returnArrayIndex = 0; returnArrayIndex < numberOfElements; bufferIndex++) {
                if (buffer.get(bufferIndex) != -1) {
                    returnValuesArray.add(returnArrayIndex, buffer.get(bufferIndex));
                    buffer.set(bufferIndex, -1);
                    returnArrayIndex++;
                }
            }

            System.out.println("\tConsumer " + Thread.currentThread().getId() + " consumed   \t" + numberOfElements + " units");

            consumerAlreadyWaiting = false;
            otherConsumers.signal();
            firstProducer.signal();
            return returnValuesArray;
        } finally {
            lock.unlock();
        }
    }

    private boolean notEnoughUnitsInBuffer(int numberOfElements) {
        return bufferElementsCount < numberOfElements;
    }
}
