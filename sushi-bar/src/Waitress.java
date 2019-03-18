import java.util.Random;

/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    private WaitingArea waitingArea;

    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        Random rnd = new Random();
        while(SushiBar.isOpen || !waitingArea.buffer.isEmpty()){

            Customer customer = waitingArea.next();
            SushiBar.write(SushiBar.genStr(Thread.currentThread().getName(), customer.getCustomerID(), "fetched"));

            int delay = rnd.nextInt(SushiBar.waitressWait);
            while(delay > 0){ delay--;}
            customer.order();
        }
    }


}

