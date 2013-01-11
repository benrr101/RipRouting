import java.util.LinkedList;

/**
 * Connection class
 * @descrip Connection class to be used as a pipe between router threads.
 * @author Benjamin Russell (brr1922)
 */
public class Connection extends LinkedList<RoutingTable> {
    // MEMBER VARIABLES ////////////////////////////////////////////////////
    /**
     * The link speed for the link (aka the metric)
     */
    private final int linkSpeed;

    /**
     * Whether or not the link is down
     */
    private boolean down = false;

    // CONSTRUCTORS ////////////////////////////////////////////////////////

    public Connection(int linkSpeed) {
        // Set the link speed
        this.linkSpeed = linkSpeed;
    }

    // METHODS /////////////////////////////////////////////////////////////

    /**
     * Receives whatever is at the head of the pipe
     * @return  A routing table, if there is one, null otherwise
     * @throws NullPointerException Thrown if the link is down
     */
    public RoutingTable receive() throws NullPointerException {
        // Verify that the link is up before returning something
        if(down) {
            throw new NullPointerException("Link is down!");
        }

        // Return whatever is at the head of the list, or nothing
        return (super.size() > 0) ? super.removeFirst() : null;
    }

    /**
     * Adds the given routing table to the list pipe
     * @param r The routing table to send
     * @throws NullPointerException Thrown if the link is down
     */
    public void send(RoutingTable r) throws NullPointerException {
        if(down) {
            throw new NullPointerException("Link is Down!");
        }

        super.add(r);
    }

    // GETTERS/SETTERS /////////////////////////////////////////////////////

    /**
     * @return The link speed
     */
    public int getLinkSpeed() { return linkSpeed; }
}
