package zad1.activeObject;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Małgorzata Salawa
 */
public class Servant<T> {

    private final int MAX_SIZE;
    private Queue<T> buffer;

    public Servant(int MAX_SIZE) {
        this.MAX_SIZE = MAX_SIZE;
        this.buffer = new ConcurrentLinkedQueue<T>();
    }

    public void put(T element) {
        if(isFull()) {
            throw new IllegalStateException("Putting to full queue!");
        }
        buffer.offer(element);
    }

    public T take() {
        if(isEmpty()) {
            throw new IllegalStateException("Taking from empty queue!");
        }
        return buffer.poll();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public boolean isFull() {
        return buffer.size() == MAX_SIZE;
    }
}
