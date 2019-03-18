import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;

/**
 * Bounded implemented as a circular buffer
 *
 */
public class CustomerBuffer {

    private int bound;
    private Customer[] buffer;
    private int count = 0;
    private int nextIn = 0;
    private int nextOut = 0;

    public CustomerBuffer(int bound) {
        this.bound = bound;
        this.buffer = new Customer[bound];
    }

    public boolean isFull(){
        return count == bound;
    }
    public boolean isEmpty() {return count == 0;}

    public void put(Customer customer){
        if (this.isFull()){
            throw new BufferOverflowException();
        } else {
            buffer[nextIn] = customer;
            count++;
            nextIn = ++nextIn % bound;
        }
    }

    public Customer pop(){
        if(this.isEmpty()){
            throw new BufferUnderflowException();
        } else {
            Customer c = buffer[nextOut];
            count--;
            nextOut = ++nextOut % bound;
            return c;
        }
    }

    public Customer peek(){
        if(this.isEmpty()){
            throw new BufferUnderflowException();
        } else {
            return buffer[nextOut];
        }
    }

}
