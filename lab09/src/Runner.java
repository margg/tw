import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * @author Małgorzata Salawa
 */
public class Runner extends JFrame {

    private final int width;
    private final int height;
    private List<Future<List<MandelbrotResult>>> futures = new ArrayList<Future<List<MandelbrotResult>>>();
    private List<MandelbrotResult> results = new ArrayList<MandelbrotResult>();
    private BufferedImage I;

    public Runner() throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        width = 800;
        height = 600;
        setBounds(100, 100, width, height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        I = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        for (int threadsCount = 1; threadsCount <= 50; threadsCount += 1) {


            ExecutorService service = Executors.newWorkStealingPool(threadsCount);

            int x1 = 0;
            int y1 = 0;
            int x2 = width;
            int y2 = 0;
            int rowHeight = height / threadsCount;

            long timeStart = System.currentTimeMillis();
            for (int i = 0; i < threadsCount - 1; i++) {
                y2 = y1 + rowHeight;
                futures.add(service.submit(new Mandelbrot(x1, y1, x2, y2)));
                y1 = y2;
            }
            futures.add(service.submit(new Mandelbrot(x1, y1, x2, height)));

            for (Future<List<MandelbrotResult>> future : futures) {
                results.addAll(future.get());
            }
            long timeStop = System.currentTimeMillis();

            for (MandelbrotResult result : results) {
                int x = result.getX();
                int y = result.getY();
                int value = result.getValue();
                I.setRGB(x, y, value);
//            System.out.println(x + "\t " + y + "\t " + value);
            }

            System.out.println(threadsCount + ", " + (timeStop - timeStart));
            service.shutdown();

            futures = new ArrayList<Future<List<MandelbrotResult>>>();
            results = new ArrayList<MandelbrotResult>();
        }

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Runner().setVisible(true);
    }
}