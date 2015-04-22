/**
 * Created by student14 on 2015-03-11.
 */
public class Counter {
    private static int count;


    public static int getCount() {
        return count;
    }

    //    public static void increment() {
    public synchronized static void increment() {
        count++;
    }

    //    public static void decrement() {
    public synchronized static void decrement() {
        count--;
    }
}
