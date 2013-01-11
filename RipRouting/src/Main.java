import java.util.ArrayList;

public class Main {

    private static final int ROUTERS_TO_CREATE = 10;

    private static final IpV4Addr STANDARD_SUBNET_MASK = new IpV4Addr("255.255.255.0");

    public static void main(String[] args) {
        // Create a little home for all the routers
        ArrayList<Router> routers = new ArrayList<Router>();

        // Create all the routers and start them
	    for(int i = 0; i < ROUTERS_TO_CREATE; i++) {
            // Create it
            String ipAddress = "8.8." + i + ".8";
            Router r = new Router(new IpV4Addr(ipAddress), STANDARD_SUBNET_MASK);
            routers.add(r);

            // Start it
            r.start();
        }
    }
}
