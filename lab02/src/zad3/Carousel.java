package zad3;

import static java.lang.Thread.sleep;

/**
 * @author Małgorzata Salawa
 */
public class Carousel {

    private static BoundedSemaphore carousel = new BoundedSemaphore(2, 1, 5);

    public static void main(String[] args) throws InterruptedException {

        Runnable child = new Runnable() {
            @Override
            public void run() {
                try {
                    carousel.take();
                    System.out.println(Thread.currentThread().getId() + " carousel.take()");
                    sleep(1000);
                    carousel.give();
                    System.out.println(Thread.currentThread().getId() + " \t\tcarousel.give()");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread c1 = new Thread(child);
        Thread c2 = new Thread(child);
        Thread c3 = new Thread(child);
        Thread c4 = new Thread(child);
        Thread c5 = new Thread(child);
        Thread c6 = new Thread(child);
        Thread c7 = new Thread(child);
        Thread c8 = new Thread(child);
        Thread c9 = new Thread(child);

        c1.start();
        c2.start();
        sleep(600);
        c3.start();
        c4.start();
        c5.start();
        c6.start();
        c7.start();
        sleep(1500);
        c8.start();
        c9.start();

    }


}
