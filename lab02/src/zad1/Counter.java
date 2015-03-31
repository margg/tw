package zad1;

/**
 * Created by student14 on 2015-03-11.
 */
public class Counter {
    private int count;
    private Semaphore semaphore;

    public Counter(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        semaphore.take();

        count++;

        semaphore.give();
    }

    public void decrement() {
        semaphore.take();

        count--;

        semaphore.give();
    }
}
