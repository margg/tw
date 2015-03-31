package zad2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * @author Małgorzata Salawa
 */
public class PrinterMonitor {


    private static final int N = 4;
    private static Lock frontLock = new ReentrantLock();
    private static Lock backLock = new ReentrantLock();
    private static Condition notEmpty = frontLock.newCondition();
    private static Deque<Integer> printersQueue = new LinkedList<Integer>();

    public static void main(String[] args) {

        Runnable process = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                        createDoc();
                        int printerId = PrinterMonitor.reserve();
                        print(printerId);
                        PrinterMonitor.release(printerId);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread tc1 = new Thread(process);
        Thread tc2 = new Thread(process);
        Thread tc3 = new Thread(process);
        Thread tc4 = new Thread(process);
        Thread tp1 = new Thread(process);
        Thread tp2 = new Thread(process);
        Thread tp3 = new Thread(process);
        Thread tp4 = new Thread(process);

        for (int i = 0; i < N; i++) {
            printersQueue.add(i);
        }


        tc1.start();
        tc2.start();
        tc3.start();
        tc4.start();
        tp1.start();
        tp2.start();
        tp3.start();
        tp4.start();


    }

    private static void print(int printerId) throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + " print() " + printerId);
        sleep(1000);
    }

    private static void release(int printerId) throws InterruptedException {
        frontLock.lock();
        try {

            System.out.println(Thread.currentThread().getId() + " release(" + printerId + ") - queue: " + printersQueue.size());

            printersQueue.addLast(printerId);
            notEmpty.signal();

        } finally {
            frontLock.unlock();
        }
    }


    private static int reserve() throws InterruptedException {
        frontLock.lock();
        try {

            while (printersQueue.isEmpty()) {
                System.out.println(Thread.currentThread().getId() + " reserve() - queue: " + printersQueue.size() + " waiting...");
                notEmpty.await();
            }

            /* while (printersQueue.size() < 2) {
                backLock.lock();
                try {
                    System.out.println("reserve() - queue: " + printersQueue.size() + "( < 2)");
                    return printersQueue.removeFirst();
                } finally {
                    backLock.unlock();
                }
            }*/

            System.out.println(Thread.currentThread().getId() + " reserve() - queue: " + printersQueue.size() + ", FIRST: " + printersQueue.peekFirst());

            return printersQueue.removeFirst();

        } finally {
            frontLock.unlock();
        }
    }

    private static void createDoc() throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + " createDoc()");
        sleep(500);
    }


}
