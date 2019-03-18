import java.util.ArrayList;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {


    public CustomerBuffer buffer;
    public Door door;

    /**
     * Creates a new waiting area.
     *
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        buffer = new CustomerBuffer(size);
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) {
        synchronized (door){
            while(buffer.isFull()){
                try {
                    door.wait();
                } catch (InterruptedException e) { }
            }
            buffer.put(customer);
            notifyAll();
        }
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        synchronized (this){
            while(buffer.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException e) { }
            }

            Customer customer = buffer.pop();
            door.notify();
            return customer;
        }
    }

    // Add more methods as you see fit
}
