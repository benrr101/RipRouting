import java.net.Inet4Address;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Router Class
 * @descrip This class represents a router. It will act as an independent
 *          thread. It has connections between different routers.
 * @author  Benjamin Russell (brr1922)
 * @file    Router.java
 */
public class Router extends Thread {

    // MEMBER VARIABLES ////////////////////////////////////////////////////
    private final Inet4Address ipAddress;

    private final Inet4Address subnetMask;

    private List<Connection> connections;

    private RoutingTable table;

    private Date nextBroadcast;

    private static final int BROADCAST_DELAY_SECONDS = 10;

    // CONSTRUCTOR /////////////////////////////////////////////////////////
    /**
     * Constructor
     * @param ipAddress
     * @param subnetMask
     */
    public Router(Inet4Address ipAddress, Inet4Address subnetMask) {
        // Assign the ip and mask
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;

        // Build the initial routing table

        // Broadcast!
        broadcast();

    }

    // THREAD METHODS //////////////////////////////////////////////////////
    public void run() {
        while(true) {
            // Step 1: Is there any data to be received?
            for(Connection c : connections) {
                updateRoutingTable(c.receive());
            }

            // Step 2: Is it time to broadcast?
            if(new Date().after(nextBroadcast));
        }
    }

    // OTHER METHODS ///////////////////////////////////////////////////////

    public void addConnection(Connection c) {
        this.connections.add(c);
    }

    private void updateRoutingTable(RoutingTable table) {
        // @TODO: Do something!
    }

    private void broadcast() {
        // @TODO: Do something!

        // Set the time we last broadcast to now
        nextBroadcast = new Date(System.currentTimeMillis() +  (BROADCAST_DELAY_SECONDS * 1000));
    }
}
