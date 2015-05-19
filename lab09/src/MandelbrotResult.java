/**
 * @author Małgorzata Salawa
 */
public class MandelbrotResult {


    private final int x;
    private final int y;
    private final int value;

    public MandelbrotResult(int x, int y, int value) {

        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
}
