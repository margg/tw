import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Mandelbrot implements Callable<List<MandelbrotResult>> {

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private double zx, zy, cX, cY, tmp;


    public Mandelbrot(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public List<MandelbrotResult> call() throws Exception {
        List<MandelbrotResult> results = new ArrayList<MandelbrotResult>();

        for (int y = y1; y < y2; y++) {
            for (int x = x1; x < x2; x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                results.add(new MandelbrotResult(x, y, iter | (iter << 8)));
            }
        }
        return results;
    }
}