import java.util.Random;

/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */

    private static SynchronizedInteger nextID = new SynchronizedInteger(0);
    private final int ID;

    public Customer() {
        this.ID = Customer.generateID();
        String logStr = SushiBar.genStr(Thread.currentThread().getName(), this.ID, "created");
        SushiBar.write(logStr);
        SushiBar.customerCounter.increment();
    }


    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order(){
        Random rnd = new Random();
        int nOrders = Math.max(1,rnd.nextInt(SushiBar.maxOrder));
        int takeAwayOrders = rnd.nextInt(nOrders);
        int servedOrders = nOrders - takeAwayOrders;

        SushiBar.takeawayOrders.add(takeAwayOrders);

        // Eat
        SushiBar.write(SushiBar.genStr(Thread.currentThread().getName(), this.getCustomerID(), "eating"));

        int eatTime = rnd.nextInt(SushiBar.customerWait);
        while(eatTime > 0){ eatTime--;}

        SushiBar.servedOrders.add(servedOrders);
        SushiBar.totalOrders.add(nOrders);

        SushiBar.write(SushiBar.genStr(Thread.currentThread().getName(), this.getCustomerID(), "leaving"));
        SushiBar.customersLeft.increment();

    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        return this.ID;
    }

    /**
     *
     * @return Returns a unique, incrementing ID
     */
    public synchronized static int generateID(){
        int id = nextID.get();
        nextID.increment();
        return id;
    }

    // Add more methods as you see fit
}
