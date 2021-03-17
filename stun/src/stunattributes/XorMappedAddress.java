package stunattributes;

import java.net.InetAddress;

public class XorMappedAddress extends StunAttribute {
    private int addressFamily;
    private int port;
    private InetAddress inetAddress;

    public XorMappedAddress(int addressFamily, int port, InetAddress inetAddress) {
        super();
        this.addressFamily = addressFamily;
        this.port = port;
        this.inetAddress = inetAddress;
    }
}
