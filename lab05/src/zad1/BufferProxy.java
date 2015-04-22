package zad1;

import zad1.activeObject.ElementFuture;
import zad1.activeObject.Scheduler;
import zad1.activeObject.Servant;
import zad1.methodRequest.PutMethodRequest;
import zad1.methodRequest.TakeMethodRequest;

import java.util.concurrent.Future;

/**
 * @author Małgorzata Salawa
 */
public class BufferProxy<T> {


    private final int size;
    private Scheduler scheduler;
    private final Servant servant;
    private int requestCount = 0;

    public BufferProxy(int size, Scheduler scheduler, Servant servant) {
        this.size = size;
        this.scheduler = scheduler;
        this.servant = servant;
    }

    void put(T t) {
        if(requestCount > size ) {
            System.out.println("Too much requests. Not enqueueing.");
        } else {
            requestCount++;
            scheduler.enqueue(new PutMethodRequest(t, servant));
        }
    }

    Future<T> take() {
        if(requestCount > size) {
            System.out.println("Too much requests. Not enqueueing.");
            return null;
        } else {
            ElementFuture<T> result = new ElementFuture();
            scheduler.enqueue(new TakeMethodRequest(servant, result));
            requestCount--;
            return result;
        }
    }

}
