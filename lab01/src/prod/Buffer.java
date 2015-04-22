package prod;

/**
 * Created by student14 on 2015-03-11.
 */
public class Buffer {

    private String buff = "";

    public synchronized void put(String s) throws InterruptedException {
        while (!buff.isEmpty()) {
            wait();
        }
        buff = s;
        System.out.println("put " + buff);
        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (buff.isEmpty()) {
            wait();
        }
        String currentBuff = buff;
        System.out.println("take " + currentBuff);
        notifyAll();
        buff = "";
        return currentBuff;
    }
}
