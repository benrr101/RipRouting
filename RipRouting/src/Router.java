import java.util.ArrayList;
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
    private final IpV4Addr ipAddress;

    private final IpV4Addr subnetMask;

    private ArrayList<Connection> connections = new ArrayList<Connection>();

    private RoutingTable routingTable;

    private Date nextBroadcast = new Date(0);

    private static final int BROADCAST_DELAY_SECONDS = 10;

    // CONSTRUCTOR /////////////////////////////////////////////////////////
    /**
     * Constructor
     * @param ipAddress
     * @param subnetMask
     */
    public Router(IpV4Addr ipAddress, IpV4Addr subnetMask) {
        // Assign the ip and mask
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
    }

    // THREAD METHODS //////////////////////////////////////////////////////
    public void run() {
        while(true) {
            // Step 1: Is there any data to be received?
            for(Connection c : connections) {
                updateRoutingTable(c.receive());
            }

            // Step 2: Is it time to broadcast?
            Date currentTime = new Date();
            if(currentTime.after(nextBroadcast)) {
                broadcastRouteTable();
            }
        }
    }

    // OTHER METHODS ///////////////////////////////////////////////////////

    /**
     * Adds the connection to the list of connections that the router has and
     * adds a route to it to the routing table.
     * @param c             The connection itself
     * @param destination   The destination of the connection
     * @param subnetMask    The subnet mask for the router that we're connected to
     */
    public void addConnection(Connection c, IpV4Addr destination, IpV4Addr subnetMask) {
        // Add the connection to the list of connections
        this.connections.add(c);

        // Add a routing entry for the routing table
        RoutingEntry r = new RoutingEntry(
                destination,
                subnetMask,
                RoutingEntry.DIRECT_LINK,
                c.getLinkSpeed()
        );
        this.routingTable.add(r);
    }

    private void updateRoutingTable(RoutingTable table) {
        // @TODO: Do something!
    }

    private void broadcastRouteTable() {
        System.out.println(ipAddress + "\t Broadcasting!");
        // Send the route table to each of the connections
        for(Connection c : connections) {
            c.add(routingTable);
        }

        // Set the time we last broadcast to now
        nextBroadcast = new Date(System.currentTimeMillis() +  (BROADCAST_DELAY_SECONDS * 1000));
    }
}
