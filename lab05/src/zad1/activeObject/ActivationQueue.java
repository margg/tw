package zad1.activeObject;

import zad1.methodRequest.MethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Małgorzata Salawa
 */
public class ActivationQueue {

    private Queue<MethodRequest> queue = new ConcurrentLinkedQueue<MethodRequest>();
    private int MAX_SIZE;

    public ActivationQueue(int MAX_SIZE) {
        this.MAX_SIZE = MAX_SIZE;
    }

    public void enqueue(MethodRequest methodRequest) {
        queue.offer(methodRequest);
    }


    public MethodRequest dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isFull() {
        return queue.size() == MAX_SIZE;
    }
}
