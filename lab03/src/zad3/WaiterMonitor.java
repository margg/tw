package zad3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Małgorzata Salawa
 */
public class WaiterMonitor {

    public static void main(String[] args) {

        // special pairs for ease of tracking
        String[] SPECIAL_PAIRS = {"wariaty", "świrki", "banda dwojga"};
        int REGULAR_PAIRS_COUNT = 4;
        int PEOPLE_COUNT = (SPECIAL_PAIRS.length + REGULAR_PAIRS_COUNT) * 2;


        ExecutorService service = Executors.newFixedThreadPool(PEOPLE_COUNT);
        Restaurant restaurant = new Restaurant();

        for (String id : SPECIAL_PAIRS) {
            service.submit(new Client(id, restaurant));
            service.submit(new Client(id, restaurant));
        }

        for (int i = 1; i <= REGULAR_PAIRS_COUNT; i++) {
            String id = "generyczna para nr " + i;
            service.submit(new Client(id, restaurant));
            service.submit(new Client(id, restaurant));
        }

        service.shutdown();
    }


}
