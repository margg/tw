/**
 * Created by student14 on 2015-03-11.
 */
public class lab1 {

    private static final int N = 100000000;

    public static void main(String[] args) throws InterruptedException {
        Runnable incRun = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < N; i++) {
                    Counter.increment();
//                    System.out.println("\tincRun: " + Counter.getCount());
                }
            }
        };

        Runnable decRun = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < N; i++) {
                    Counter.decrement();
//                    System.out.println("decRun: " + Counter.getCount());
                }
            }
        };

        Thread incThread = new Thread(incRun);
        Thread decThread = new Thread(decRun);

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();

        System.out.println(Counter.getCount());

    }

}
