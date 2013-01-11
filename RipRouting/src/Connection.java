import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Omega
 * Date: 1/7/13
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Connection extends ArrayBlockingQueue<RoutingTable> {
    // MEMBER VARIABLES ////////////////////////////////////////////////////
    private final int linkSpeed;

    private boolean down = false;

    // CONSTRUCTORS ////////////////////////////////////////////////////////

    public Connection(int linkSpeed) {
        // Create a blocking queue with a store size of 1
        super(1);

        // Set the link speed
        this.linkSpeed = linkSpeed;
    }

    // METHODS /////////////////////////////////////////////////////////////
    public boolean canReceive() {
        return super.isEmpty();
    }

    /**
     * @throws Exception    Thrown if the blocking was interrupted.
     * @return
     */
    public RoutingTable receive() {
        // Wait until we can receive something
        try {
            return super.take();
        } catch(InterruptedException e) {
            throw new Exception("Cannot receive. Blocking was interrupted.", e);
        }
    }
}
