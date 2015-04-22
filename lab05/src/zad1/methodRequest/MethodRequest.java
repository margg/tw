package zad1.methodRequest;

/**
 * @author Małgorzata Salawa
 */
public interface MethodRequest<T> {

    boolean guard();

    <T> void call();

}
