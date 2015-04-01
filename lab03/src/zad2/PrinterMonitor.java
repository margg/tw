package zad2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * @author Małgorzata Salawa
 */
public class PrinterMonitor {


    private static final int PROCESS_COUNT = 30;
    private static final int PRINTERS_COUNT = 10;
    private static Lock lock = new ReentrantLock();
    private static Condition notEmpty = lock.newCondition();
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

        ExecutorService service = Executors.newFixedThreadPool(PROCESS_COUNT);

        for (int i = 0; i < PRINTERS_COUNT; i++) {
            printersQueue.add(i);
        }

        for (int i = 0; i < PROCESS_COUNT; i++) {
            service.submit(new Thread(process));
        }

        service.shutdown();

    }

    private static void release(int printerId) throws InterruptedException {
        lock.lock();
        try {

            System.out.println(Thread.currentThread().getId() + " release(" + printerId + ") - queue size: " + printersQueue.size());

            printersQueue.addLast(printerId);
            notEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    private static int reserve() throws InterruptedException {
        lock.lock();
        try {

            while (printersQueue.isEmpty()) {
                System.out.println(Thread.currentThread().getId() + " reserve() - queue size: " + printersQueue.size() + " waiting...");
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

            System.out.println(Thread.currentThread().getId() + " reserve() - queue size: " + printersQueue.size() + ", FIRST: " + printersQueue.peekFirst());

            return printersQueue.removeFirst();

        } finally {
            lock.unlock();
        }
    }


    private static void print(int printerId) throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + " print() \t" + printerId);
        sleep(1000);
    }

    private static void createDoc() throws InterruptedException {
        System.out.println(Thread.currentThread().getId() + " createDoc()");
        sleep(500);
    }


}
