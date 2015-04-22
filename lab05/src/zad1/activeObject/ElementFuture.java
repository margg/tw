package zad1.activeObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

/**
 * @author Małgorzata Salawa
 */
public class ElementFuture<T> implements Future<T> {
    private T result;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {

        while(result == null) {
//            System.out.println("Result null, still waiting!");
            sleep(100);
        }
        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    public void setResult(T t) {
        result = t;
    }
}
