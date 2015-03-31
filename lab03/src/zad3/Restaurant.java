package zad3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Małgorzata Salawa
 */
public class Restaurant {

    private Table table = Table.EMPTY;

    private Lock lock = new ReentrantLock();

    Map<String, Condition> conditionsMap = new HashMap<String, Condition>();
    private Condition notEmptyTable = lock.newCondition();

    public void reserveTable(String id) throws InterruptedException {

        lock.lock();
        try {

            if (conditionsMap.containsKey(id)) {

                System.out.println("Found pair\t" + id);
                System.out.println("Waiting for table being empty...\t" + id);
                while (table != Table.EMPTY) {
                    notEmptyTable.await();
                }
                System.out.println("Got table\t" + id);


                Condition condition = conditionsMap.get(id);
                table = Table.ONE_PERSON;
                System.out.println("At the table\t" + id);
                conditionsMap.remove(id);
                System.out.println("Telling pair not to wait anymore\t" + id);
                condition.signal();

            } else {
                System.out.println("Starting to wait\t" + id);
                Condition condition = lock.newCondition();
                conditionsMap.put(id, condition);
                while (conditionsMap.containsKey(id)) {
                    condition.await();
                }
                System.out.println("Got info not to wait\t" + id);
                table = table.join();
                System.out.println("Joined table\t" + id);
            }

        } finally {
            lock.unlock();
        }
    }

    public void leaveTable() {
        lock.lock();
        try {

            table = table.leave();
            System.out.println("Left table, now\t" + table);

            if (table == Table.EMPTY) {
                System.out.println("Table ready to be taken!\t");
                notEmptyTable.signal();
            }

        } finally {
            lock.unlock();
        }
    }


}
