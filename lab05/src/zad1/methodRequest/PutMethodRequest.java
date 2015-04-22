package zad1.methodRequest;

import zad1.activeObject.Servant;

/**
 * @author Małgorzata Salawa
 */
public class PutMethodRequest<T> implements MethodRequest<T> {
    private final T element;
    private final Servant servant;

    public PutMethodRequest(T t, Servant servant) {
        this.element = t;
        this.servant = servant;
    }

    @Override
    public boolean guard() {
        return !servant.isFull();
    }

    @Override
    public void call() {
        servant.put(element);
    }
}
