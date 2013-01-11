import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: Benjamin Russell
 * My own representation of a simple IpV4Address. Why? Because Java is silly.
 */
public class IpV4Addr {
    // MEMBER VARIABLES ////////////////////////////////////////////////////
    private short octet1;
    private short octet2;
    private short octet3;
    private short octet4;

    // CONSTRUCTOR /////////////////////////////////////////////////////////
    public IpV4Addr(String addr) {
        StringTokenizer tokenizer = new StringTokenizer(addr, ".");
        try {
            octet1 = Short.parseShort(tokenizer.nextToken());
            octet2 = Short.parseShort(tokenizer.nextToken());
            octet3 = Short.parseShort(tokenizer.nextToken());
            octet4 = Short.parseShort(tokenizer.nextToken());
        } catch(Exception e) {
            throw new NumberFormatException("Invalid IPv4 address.");
        }
    }

    // IMPLICIT CONVERTERS /////////////////////////////////////////////////
    public String toString() {
        return octet1 + "." + octet2 + "." + octet3 + "." + octet4;
    }

    public boolean equals(IpV4Addr addr2) {
        return this.octet1 == addr2.octet1
                && this.octet2 == addr2.octet2
                && this.octet3 == addr2.octet3
                && this.octet4 == addr2.octet4;
    }
}
