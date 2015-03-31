package zad1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        BoundedBuffer buffer = new BoundedBuffer();

        Consumer c1 = new Consumer(buffer);
        Consumer c2 = new Consumer(buffer);
        Consumer c3 = new Consumer(buffer);
        Consumer c4 = new Consumer(buffer);

        Producer p1 = new Producer(buffer);
        Producer p2 = new Producer(buffer);
        Producer p3 = new Producer(buffer);
        Producer p4 = new Producer(buffer);

        Thread tc1 = new Thread(c1);
        Thread tc2 = new Thread(c2);
        Thread tc3 = new Thread(c3);
        Thread tc4 = new Thread(c4);
        Thread tp1 = new Thread(p1);
        Thread tp2 = new Thread(p2);
        Thread tp3 = new Thread(p3);
        Thread tp4 = new Thread(p4);

        tc1.start();
        tc2.start();
        tc3.start();
        tc4.start();
        tp1.start();
        tp2.start();
        tp3.start();
        tp4.start();


    }
}