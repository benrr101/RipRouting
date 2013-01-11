/**
 * Created with IntelliJ IDEA.
 * User: Omega
 * Date: 1/10/13
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoutingEntry {
    // CONSTANTS ///////////////////////////////////////////////////////////

    public static final IpV4Addr DIRECT_LINK = new IpV4Addr("0.0.0.0");

    // MEMBER VARIABLES ////////////////////////////////////////////////////

    private IpV4Addr destination;

    private IpV4Addr destinationSubnetMask;

    private IpV4Addr nextHop;

    private int metric;

    // CONSTRUCTOR /////////////////////////////////////////////////////////

    public RoutingEntry(IpV4Addr destination, IpV4Addr subnetMask, IpV4Addr nextHop, int metric) {
        this.destination = destination;
        this.destinationSubnetMask = subnetMask;
        this.nextHop = nextHop;
        this.metric = metric;
    }

    // GETTERS/SETTERS /////////////////////////////////////////////////////
    public void setDestination(IpV4Addr destination) {
        this.destination = destination;
    }

    public void setDestinationSubnetMask(IpV4Addr subnetMask) {
        this.destinationSubnetMask = subnetMask;
    }

    public void setNextHop(IpV4Addr nextHop) {
        this.nextHop = nextHop;
    }

    public void setMetric(int metric) {
        this.metric = metric;
    }

    public IpV4Addr getDestination() { return destination; }
    public IpV4Addr getDestinationSubnetMask() { return destinationSubnetMask; }
    public IpV4Addr getNextHop() { return nextHop; }
    public int getMetric() { return metric; }
}
