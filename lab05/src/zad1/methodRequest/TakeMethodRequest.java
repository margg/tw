package zad1.methodRequest;

import zad1.activeObject.ElementFuture;
import zad1.activeObject.Servant;

/**
 * @author Małgorzata Salawa
 */
public class TakeMethodRequest<T> implements MethodRequest<T> {
    private final Servant servant;
    private ElementFuture<T> result;

    public TakeMethodRequest(Servant servant, ElementFuture<T> result) {
        this.servant = servant;
        this.result = result;
    }

    @Override
    public boolean guard() {
        return !servant.isEmpty();
    }

    @Override
    public void call() {
        T take = (T) servant.take();
        System.out.println("Taken :" + take);

        result.setResult(take);
    }
}
