import java.io.*;
import java.util.*;

/**
 * Main class where execution begins.
 * @author Benjamin Russell (brr1922)
 */
public class Main extends Thread {
    // STATIC VARIABLES ////////////////////////////////////////////////////
    private static IpV4Addr subnetMask;

    private static HashMap<IpV4Addr, Router> routers = new HashMap<IpV4Addr, Router>();
    private static ArrayList<Connection> connections = new ArrayList<Connection>();

    public static void main(String[] args) {
        boolean dynamics = args.length > 0;

        try {
            // Open the network definition file
            InputStream netStream = Thread.currentThread().getClass().getResourceAsStream("/network");
            InputStreamReader netFile = new InputStreamReader(netStream);

            // Setup parsing flags
            boolean readingRouters = false;
            boolean readingConnections = false;

            // Read it line by line
            BufferedReader buffRead = new BufferedReader(netFile);
            String strLine;
            while((strLine = buffRead.readLine()) != null) {
                // Skip empty lines
                if(strLine.isEmpty()) {
                    continue;
                }

                // What does the line say?
                StringTokenizer tokenizer = new StringTokenizer(strLine);
                if(strLine.startsWith("SUBNETMASK:")) {
                    // Reading in the subnetMask
                    String q = tokenizer.nextToken(":");
                    q = tokenizer.nextToken();
                    subnetMask = new IpV4Addr(q);
                } else if(strLine.contains("ROUTERS--")) {
                    // Reading in routers
                    readingRouters = true;
                    continue;
                } else if(strLine.contains("CONNECTIONS--") && readingRouters == true) {
                    // Start reading in connections
                    readingRouters = false;
                    readingConnections = true;
                    continue;
                }

                if(readingRouters) {
                    IpV4Addr addr = new IpV4Addr(strLine);
                    routers.put(addr, new Router(addr, subnetMask));
                } else if(readingConnections) {
                    IpV4Addr routerAAddr = new IpV4Addr(tokenizer.nextToken(" "));
                    IpV4Addr routerBAddr = new IpV4Addr(tokenizer.nextToken(" "));
                    int linkCost = Integer.parseInt(tokenizer.nextToken());

                    Router routerA = findRouter(routerAAddr);
                    Router routerB = findRouter(routerBAddr);
                    if(routerA == null || routerB == null) {
                        throw new NullPointerException("Connection expression '" + strLine + "' references invalid routers");
                    }

                    // Create the connection and add it
                    Connection c = new Connection(linkCost, dynamics);
                    connections.add(c);
                    routerA.addConnection(c, routerB.getIpAddress(), routerB.getSubnetMask());
                    routerB.addConnection(c, routerA.getIpAddress(), routerA.getSubnetMask());
                }

            }
        } catch(FileNotFoundException e) {
            System.err.println("*** Cannot find network definition file!");
            System.exit(1);
        } catch(Exception e) {
            System.err.println("*** Exception occurred: " + e);
            System.exit(1);
        }

        // Output time headers
        System.out.println("TIME:0 ---------------------");
        int time = 0;

        // Start the simulation
        for(Router r : routers.values()) {
            r.start();
        }

        while(time < 20) {
            try {
                sleep(1990);
            } catch(Exception e) {}
            time++;
            System.out.println("TIME:" + time + " ---------------------");
        }

        // Stop the simulation
        System.exit(0);
    }

    /**
     * Finds a router by it's ip address. This method is written because Java is silly.
     * @param addr  The address to search for
     * @return  The router that matches the given ip address
     */
    private static Router findRouter(IpV4Addr addr) {
        for(Map.Entry<IpV4Addr, Router> entry : routers.entrySet()) {
            if(entry.getKey().equals(addr)) { return entry.getValue(); }
        }
        return null;
    }
}
