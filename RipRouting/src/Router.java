import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private HashMap<IpV4Addr, Connection> connections = new HashMap<IpV4Addr, Connection>();

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

        // Create the initial routing table
        routingTable = new RoutingTable(this.ipAddress);
    }

    // THREAD METHODS //////////////////////////////////////////////////////
    public void run() {
        while(true) {
            // Step 1: Is there any data to be received?
            for(Map.Entry<IpV4Addr, Connection> entry: connections.entrySet()) {
                RoutingTable table = entry.getValue().receive();
                if(table == null) {
                    // Nothing to receive yet...
                    continue;
                }
                updateRoutingTable(table, entry.getKey());
            }

            // Step 2: Is it time to broadcast?
            Date currentTime = new Date();
            if(currentTime.after(nextBroadcast)) {
                broadcastRouteTable();
                System.out.println(routingTable.toString());
            } else {
                // Sleep! Save my CPU!
                try {
                    sleep(500);
                } catch(InterruptedException e) {
                    // Well, might as well keep going...
                }
            }
        }
    }

    // METHODS /////////////////////////////////////////////////////////////

    /**
     * Adds the connection to the list of connections that the router has and
     * adds a route to it to the routing table.
     * @param c             The connection itself
     * @param destination   The destination of the connection
     * @param subnetMask    The subnet mask for the router that we're connected to
     */
    public void addConnection(Connection c, IpV4Addr destination, IpV4Addr subnetMask) {
        // Add the connection to the list of connections
        if(connections.containsKey(destination)) {
            // Duplicate connection
            return;
        }
        this.connections.put(destination, c);

        // Add a routing entry for the routing table
        RoutingEntry r = new RoutingEntry(
                destination,
                subnetMask,
                RoutingEntry.DIRECT_LINK,
                c.getLinkSpeed()
        );
        this.routingTable.put(destination, r);
    }

    /**
     *
     * @param table
     * @param source
     */
    private void updateRoutingTable(RoutingTable table, IpV4Addr source) {
        // Iterate over the connections that came into
        for(RoutingEntry entry : table.values()) {
            // Check to see if we already have a route to this network
            if(this.routingTable.containsKey(entry.getDestination())) {
                // It does exist. Is it a faster link?
                RoutingEntry ourEntry = routingTable.get(entry.getDestination());
                if(ourEntry.getMetric() > entry.getMetric() + connections.get(source).getLinkSpeed()) {
                    // It's a better link, we should update our routes to use it!
                    ourEntry.setNextHop(source);
                    ourEntry.setMetric(entry.getMetric() + connections.get(source).getLinkSpeed());
                }
            } else {
                // It does not exist. We should add it straight-up
                RoutingEntry newEntry = new RoutingEntry(
                        entry.getDestination(),
                        entry.getDestinationSubnetMask(),
                        entry.getNextHop(),
                        entry.getMetric()
                );
                this.routingTable.put(entry.getDestination(), newEntry);
            }
        }
    }

    private void broadcastRouteTable() {
        System.out.println(ipAddress + "\t Broadcasting!");
        // Send the route table to each of the connections
        for(Connection c : connections.values()) {
            c.send(routingTable);
        }

        // Set the time we last broadcast to now
        nextBroadcast = new Date(System.currentTimeMillis() +  (BROADCAST_DELAY_SECONDS * 1000));
    }

    // GETTERS /////////////////////////////////////////////////////////////
    public IpV4Addr getIpAddress() { return ipAddress; }
    public IpV4Addr getSubnetMask() { return subnetMask; }
    public int getConnectionCount() { return connections.size(); }
}
