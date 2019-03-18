import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 15;
    private static int waitressCount = 8;
    private static int duration = 4;
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 2000; // Used to calculate the time the customer spends eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;


    public static void main(String[] args) {
        log = new File(path + "log.txt");

        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);

        Clock clock = new Clock(duration);


        WaitingArea waitingArea = new WaitingArea(SushiBar.waitingAreaCapacity);
        Door door = new Door(waitingArea);
        Waitress[] waitresses = new Waitress[SushiBar.waitressCount];

        door.run();

        for(int i = 0; i < waitressCount; i++){
            waitresses[i] = new Waitress(waitingArea);
            waitresses[i].run();
        }

    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String genStr(String threadName, int customerID, String action){
        return String.format("%s: Customer #%d is now %s", threadName, customerID, action);
    }

}
