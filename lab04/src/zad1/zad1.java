package zad1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Małgorzata Salawa
 */
public class zad1 {


    public static void main(String[] args) {

        int BUFFER_SIZE = 100;

        int PRODUCERS_COUNT = 1;
        int PROCESSORS_COUNT = 5;
        int CONSUMERS_COUNT = 1;

        int SUM_OF_CLIENTS = PRODUCERS_COUNT + PROCESSORS_COUNT + CONSUMERS_COUNT;
        ExecutorService service = Executors.newFixedThreadPool(SUM_OF_CLIENTS);

        Buffer buffer = new Buffer(BUFFER_SIZE, SUM_OF_CLIENTS);

        buffer.setUp();

        service.submit(new Client(buffer, 0));
        for (int i = 1; i <= PROCESSORS_COUNT; i++) {
            service.submit(new Client(buffer, i));
        }
        service.submit(new Client(buffer, PROCESSORS_COUNT + 1));


        service.shutdown();
    }








    /*
    *
    * na początku mamy -1 wszędzie
    *
    *
    *
    * konsument zamienia N-1 na -1
    *
    * procesy "biegają cykliczne" po buforze, nie mogą się przeganiać -> jak taśma produkcyjna
    *
    *
    * tyle semaforów, ile procesów !!
    *
    *
    *
    * 2.
    *
    * bufor - parzysta liczba miejsc
    *
    * dużo producentów, dużo konsumentów
    *   - losują ile chcą wyprodukować/skonsumować  - [1, M/2]
    *
    *   kolejność nie ma znaczenia, ważna jest tylko ilość
    *
    *   PROBLEM -> jeżeli jest cały wypełniony
    *       -> jeżeli będzie mało miejsca - gruby producent nigdy nie zostanie wybrany -> zagłodzenie
    *
    *       - nie robić priorytetami wątków, tylko coś przy pomocy semaforów, locków
    *
    *       drugi problem -> zakleszczenie
    *
    *
    *
    *
    *
    *
    * */


}
