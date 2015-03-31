package zad3;

import static java.lang.Thread.sleep;

/**
 * @author Małgorzata Salawa
 */
public class Client implements Runnable {


    private final String id;
    private Restaurant restaurant;

    public Client(String id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (true) {

                prepare();

                System.out.println("At the restaurant\t" + id);
                restaurant.reserveTable(id);
                System.out.println("oM NOM NOM!\t" + id);
                eat();
                restaurant.leaveTable();
                System.out.println("Said bye...\t" + id);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void eat() throws InterruptedException {
        sleep(1000);
    }

    private static void prepare() throws InterruptedException {
        sleep(500);
    }

}
