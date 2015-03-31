package zad1;

/**
 * Created by student14 on 2015-03-11.
 */
public class zad1 {

    private static final int N = 10000;

    public static void main(String[] args) throws InterruptedException {

        BinarySemaphore binSem = new BinarySemaphore();
        final Counter counter = new Counter(binSem);

        Runnable incRun = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < N; i++) {
                    counter.increment();
                    System.out.println("\tincRun: " + counter.getCount());
                }
            }
        };

        Runnable decRun = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < N; i++) {
                    counter.decrement();
                    System.out.println("decRun: " + counter.getCount());
                }
            }
        };


        Thread incThread = new Thread(incRun);
        Thread decThread = new Thread(decRun);

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();

        System.out.println(counter.getCount());

    }

}
