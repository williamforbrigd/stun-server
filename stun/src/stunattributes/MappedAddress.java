package stunattributes;

import java.net.InetAddress;

/**
 * Indicates a reflexive transport address of the client.
 */
public class MappedAddress extends StunAttribute {
    private int addressFamily;
    private int port;
    private InetAddress inetAddress;

    public MappedAddress(int addressFamily, int port, InetAddress inetAddress) {
        super();
        this.addressFamily = addressFamily;
        this.port = port;
        this.inetAddress = inetAddress;
    }
}
