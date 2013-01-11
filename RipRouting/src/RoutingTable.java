import java.util.ArrayList;

public class RoutingTable extends ArrayList<RoutingEntry> {
    // MEMBER VARIABLES ////////////////////////////////////////////////////
    private IpV4Addr owner;

    // CONSTRUCTOR /////////////////////////////////////////////////////////
    public RoutingTable(IpV4Addr owner) {
        this.owner = owner;
    }

    // IMPLICIT CONVERTERS /////////////////////////////////////////////////
    public String toString() {
        // Start with nothing
        StringBuilder output = new StringBuilder();

        // Output a header
        output.append(owner + " Routing Table\n");
        output.append("<destination IP>\t<dest subnet mask>\t<next hop>\t<metric>\n");

        // Output info for each routing table
        for(RoutingEntry entry : this) {
            output.append("    " + entry.getDestination()
                    + "            " + entry.getDestinationSubnetMask()
                    + "     " + entry.getNextHop()
                    + "        " + entry.getMetric()
                    + "\n"
            );
        }

        output.append("---\n");

        return output.toString();
    }
}


