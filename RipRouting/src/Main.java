import java.util.ArrayList;
import java.util.Random;

/**
 * Main class where execution begins.
 * @author Benjamin Russell (brr1922)
 */
public class Main {
    // CONSTANTS ///////////////////////////////////////////////////////////
    private static final int ROUTERS_TO_CREATE = 10;
    private static final int MAX_LINK_COST = 20;

    private static final IpV4Addr STANDARD_SUBNET_MASK = new IpV4Addr("255.255.255.0");

    public static void main(String[] args) {
        // Create a little home for all the routers and connections
        ArrayList<Router> routers = new ArrayList<Router>();
        ArrayList<Connection> connections = new ArrayList<Connection>();

        // Create all the routers and start them
	    for(int i = 0; i < ROUTERS_TO_CREATE; i++) {
            // Create it
            String ipAddress = "8.8." + i + ".8";
            Router r = new Router(new IpV4Addr(ipAddress), STANDARD_SUBNET_MASK);
            routers.add(r);
        }

        Random random = new Random();

        // Create connections between the nodes
        for(int i = 0; i < ROUTERS_TO_CREATE * 3; i++) {
            // Randomly select two routers to connect
            int routerIndexA = random.nextInt(ROUTERS_TO_CREATE - 1);
            int routerIndexB = random.nextInt(ROUTERS_TO_CREATE - 1);
            while(routerIndexA == routerIndexB) {
                // We cannot connect to ourselves
                routerIndexA = random.nextInt(ROUTERS_TO_CREATE - 1);
                routerIndexB = random.nextInt(ROUTERS_TO_CREATE - 1);
            }

            // Create a connection
            Connection connection = new Connection(random.nextInt(MAX_LINK_COST - 1) + 1);

            // Add the connection to the two routers
            Router routerA = routers.get(routerIndexA);
            Router routerB = routers.get(routerIndexB);
            routerA.addConnection(connection, routerB.getIpAddress(), routerB.getSubnetMask());
            routerB.addConnection(connection, routerA.getIpAddress(), routerA.getSubnetMask());
        }

        // Start up the routers and check for unconnected routers. They won't be happy.
        for(Router r : routers) {
            if(r.getConnectionCount() == 0) {
                // Create a new connection for the unconnected router
                Connection connection = new Connection(random.nextInt(MAX_LINK_COST - 1) + 1);
                connections.add(connection);

                Router routerA = r;
                Router routerB = routers.get(0);
                if(routerA.equals(routerB)) {
                    routerB = routers.get(1);
                }

                // Add the connection to the routers
                routerA.addConnection(connection, routerB.getIpAddress(), routerB.getSubnetMask());
                routerB.addConnection(connection, routerA.getIpAddress(), routerA.getSubnetMask());
            }

            // Start it!
            System.out.println(r.getRoutingTable());
        }
    }
}
