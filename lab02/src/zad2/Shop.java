package zad2;

import static java.lang.Thread.sleep;

/**
 * @author Małgorzata Salawa
 */
public class Shop {

    private static CountingSemaphore carts = new CountingSemaphore(3);

    public static void main(String[] args) throws InterruptedException {


        Runnable customer = new Runnable() {
            @Override
            public void run() {
                try {
                    carts.take();
                    System.out.println(Thread.currentThread().getId() + " carts.take()");
                    sleep(1000);
                    carts.give();
                    System.out.println(Thread.currentThread().getId() + " \t\tcarts.give()");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread c1 = new Thread(customer);
        Thread c2 = new Thread(customer);
        Thread c3 = new Thread(customer);
        Thread c4 = new Thread(customer);
        Thread c5 = new Thread(customer);
        Thread c6 = new Thread(customer);
        Thread c7 = new Thread(customer);
        Thread c8 = new Thread(customer);
        Thread c9 = new Thread(customer);

        c1.start();
        c2.start();
        c3.start();
        sleep(600);
        c4.start();
        c5.start();
        c6.start();
        sleep(1500);
        c7.start();
        c8.start();
        c9.start();

    }



}
