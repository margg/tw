package zad3;

import zad1.Semaphore;
import zad2.CountingSemaphore;

/**
 * @author Małgorzata Salawa
 */
public class BoundedSemaphore implements Semaphore {

    private CountingSemaphore protecting;
    private CountingSemaphore queueing;

    public BoundedSemaphore(int leases, int minimum, int maximum) {
        protecting = new CountingSemaphore(minimum + leases);
        queueing = new CountingSemaphore(maximum - leases);
    }

    @Override
    public void take() {
        protecting.take();
//        System.out.println(Thread.currentThread().getId() + " TAKE -> prot  " + protecting.getLeases());
        queueing.give();
//        System.out.println(Thread.currentThread().getId() + " TAKE -> queue " + queueing.getLeases());
    }

    @Override
    public void give() {
        queueing.take();
//        System.out.println(Thread.currentThread().getId() + " GIVE -> prot  " + protecting.getLeases());
        protecting.give();
//        System.out.println(Thread.currentThread().getId() + " GIVE -> queue " + queueing.getLeases());
    }
}
