package zad2;

import zad1.BinarySemaphore;
import zad1.Semaphore;

/**
 * @author Małgorzata Salawa
 */
public class CountingSemaphore implements Semaphore {

    private int leases;

    public CountingSemaphore(int leases) {
        this.leases = leases;
    }

    Semaphore protecting = new BinarySemaphore();
    Semaphore queueing = new BinarySemaphore();

    @Override
    public void take() {
        while (true) {
            protecting.take();
            if (leases > 0) {
                leases--;
                protecting.give();
                return;
            } else {
                protecting.give();
                queueing.take();
            }
        }
    }

    @Override
    public void give() {
        protecting.take();
        leases++;
        protecting.give();
        queueing.give();
    }

    public int getLeases() {
        return leases;
    }
}
