package prod;

/**
 * Created by student14 on 2015-03-11.
 */
public class producers {

    public static final int N = 100;

    public static void main(String[] args) {

        Buffer buffer = new Buffer();

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
