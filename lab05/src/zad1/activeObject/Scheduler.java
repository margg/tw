package zad1.activeObject;

import zad1.methodRequest.MethodRequest;


/**
 * @author Małgorzata Salawa
 */
public class Scheduler {
//    private final int MAX_SIZE;
    private ActivationQueue queue;

    public Scheduler(int MAX_SIZE) {
//        this.MAX_SIZE = MAX_SIZE;
        this.queue = new ActivationQueue(MAX_SIZE);
    }

    public void enqueue(MethodRequest methodRequest) {
        if(queue.isFull()) {
            System.out.println("ActivationQueue full!");
        } else {
            queue.enqueue(methodRequest);
        }
    }

    public void run() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dispatch();
            }
        });
        thread.start();
    }

    private void dispatch() {
        while (true) {
            if(!queue.isEmpty()) {
                MethodRequest request = queue.dequeue();
                //System.out.println("Dequeueing: " + request);

                if (request.guard()) {
//                    System.out.println("Calling request: " + request);
                    request.call();

                } else {
                    queue.enqueue(request);
                }
            }
        }
    }

}
