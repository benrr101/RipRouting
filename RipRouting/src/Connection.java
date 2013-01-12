import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

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
    private final int linkCost;

    /**
     * Whether or not the link is down
     */
    private boolean down = false;

    private int downProbability;

    private Date nextCheck = new Date();

    // CONSTRUCTORS ////////////////////////////////////////////////////////

    public Connection(int linkCost, boolean canGoDown) {
        // Set the link speed
        this.linkCost = linkCost;
        downProbability = (canGoDown) ? 2 : -1;

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

        // Will we go down?
        Random r = new Random();
        if(new Date().after(nextCheck)) {
            if(r.nextInt(10) <= downProbability) {
                // OH NO! we're going down!
                down = true;
                throw new NullPointerException("Link is down!");
            } else {
                // Link is back up
                down = false;
            }
        }

        // Update when we next check
        nextCheck = new Date(System.currentTimeMillis() + 3000);

        // Return whatever is at the head of the list, or nothing
        try {
            return (super.size() > 0) ? super.removeFirst() : null;
        } catch(NoSuchElementException e) {
            return null;
        }
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
    public int getLinkCost() { return linkCost; }
}
