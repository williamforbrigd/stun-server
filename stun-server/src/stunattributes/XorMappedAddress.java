package stunattributes;

import client.ReflexiveAddress;
import message.StunMessage;
import message.Utility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *       0                   1                   2                   3
 *       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |x x x x x x x x|    Family     |         X-Port                |
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *      |                X-Address (Variable)
 *      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */
public class XorMappedAddress {
    private int family;
    private int xPort;
    private InetAddress xAddress;
    private byte[] buffer;
    private int bufferLength;

    public static final int IPv4_FAMILY = 0x01;
    public static final int IPv6_FAMILY = 0x02;

    public XorMappedAddress(int family, int mappedPort, InetAddress mappedAddress, long transactionID) {
        super();
        this.family = family;
        if(family == IPv4_FAMILY) {
            //The address is IPv4 and is 32 bits.
            //32 bits + 32 bits for address = 64 bits which is 8 bytes
            bufferLength = 8;
        } else if(family == IPv6_FAMILY) {
            //The address is IPv6 and is 128 bits.
            //32 bits + 128 bits for address = 160 bits which is 20 bytes
            bufferLength = 20;
        }
        buffer = new byte[bufferLength];
        buffer[0] = 0; //the first 8 bits or byte is set to 0
        buffer[1] = Utility.intToByte(family); //the family
        byte[] xPortBytes = computeXPortBytes(mappedPort);
        System.arraycopy(xPortBytes, 0, buffer, 2, 2);
        byte[] xAddressBytes = computeXAddressBytes(mappedAddress, transactionID);
        if(xAddressBytes == null) return;
        System.arraycopy(xAddressBytes, 0, buffer, 4, xAddressBytes.length); //length is 4 for IPv4 and 16 for IPv6
    }

    public XorMappedAddress(InetAddress xAddress, int xPort) {
        this.xAddress = xAddress;
        this.xPort = xPort;
    }

    public XorMappedAddress() {}

    public byte[] getBuffer() {
        return this.buffer;
    }

    /**
     * X-port is computed by taking the mapped port in host byte order, XOR'ing it with the most signifikant
     * bit of the magic cookie, and then converting the result to network byte order.
     * I assume the mapped port is already in host byte order.
     */
    private byte[] computeXPortBytes(int reflexivePort) {
        byte[] magicCookieBytes = Utility.intToFourBytes(StunMessage.MAGIC_COOKIE);
        //Most significant bits of the magic cookie are the two first bytes.
        int mostSign = Utility.twoBytesToInt(new byte[]{magicCookieBytes[0], magicCookieBytes[1]});
        this.xPort = reflexivePort ^ mostSign;
        System.out.println("\nthe xport is : " + xPort);
        //Then convert to network byte order.
        byte[] bytes = Utility.intToTwoBytes(xPort);
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).array();

        //Network byte order uses always big endian.
        //return ByteBuffer.allocate(2).putLong(xpport).order(ByteOrder.BIG_ENDIAN).array();
        //return ByteBuffer.allocate(2).putInt(xPort).order(ByteOrder.BIG_ENDIAN).array();
    }

    private static int computeReflexivePort(int xPort) {
        byte[] magicCookieBytes = Utility.intToFourBytes(StunMessage.MAGIC_COOKIE);
        int mostSign = Utility.twoBytesToInt(new byte[]{magicCookieBytes[0], magicCookieBytes[1]});
        return (xPort ^ mostSign);
    }

    /**
     * For IPv4: X-address is computed by taking the mapped IP-address in host byte order, XOR'ing it with the magic cookie,
     * and converting the result to network byte order.
     *
     * For IPv6: X-Address is computed by taking the mapped IP address
     *    in host byte order, XOR'ing it with the concatenation of the magic
     *    cookie and the 96-bit transaction ID, and converting the result to
     *    network byte order.
     */
    private byte[] computeXAddressBytes(InetAddress reflexiveAddress, long transactionID) {
        byte[] bytes = reflexiveAddress.getAddress();
        long mapped = 0, xAddressValue = 0;
        if(this.family == IPv4_FAMILY && bytes != null) {
            //IPv4 address is 4 bytes and magic cookie is 4 bytes
            mapped = Utility.fourBytesToLong(bytes);
            xAddressValue = mapped ^ StunMessage.MAGIC_COOKIE;

            return Utility.longToFourBytes(xAddressValue);
        } else if(this.family == IPv6_FAMILY && bytes != null) {
            //IPv6 address is 16 bytes
            //Magic cookie is 4 bytes. Transaction id is 12 bytes. cookie + id = 16 bytes
            mapped = ByteBuffer.wrap(bytes).getInt();
            xAddressValue = mapped ^ (StunMessage.MAGIC_COOKIE + transactionID);

            //this gives BufferOverflow
            return ByteBuffer.allocate(16).putLong(xAddressValue).array();
        }
        return null;
    }

    private static InetAddress computeReflexiveAddress(long xAddressValue, int family, long transactionID)  {
        long mappedAddressValue = 0;
        if(family == XorMappedAddress.IPv4_FAMILY) {
            mappedAddressValue = xAddressValue ^ StunMessage.MAGIC_COOKIE;
            byte[] bytes = Utility.longToFourBytes(mappedAddressValue);
            try {
                return InetAddress.getByAddress(bytes);
            } catch(UnknownHostException e) {
                System.out.println("Could not get the IPv4 address: " + e.getMessage());
            }
        } else if(family == XorMappedAddress.IPv6_FAMILY) {
            mappedAddressValue = xAddressValue ^ (StunMessage.MAGIC_COOKIE + transactionID);
            byte[] bytes = ByteBuffer.allocate(16).putLong(mappedAddressValue).array();
            try {
                return InetAddress.getByAddress(bytes);
            } catch(UnknownHostException e) {
                System.out.println("Could not get the IPv6 address: " + e.getMessage());
            }
        }
        return null;
    }

    public static ReflexiveAddress parseXorMappedAttribute(byte[] buffer, long transactionID) {
        //The 20 first bytes are the header.
        //The next bytes are the message
        ReflexiveAddress address = new ReflexiveAddress();
        int family = Utility.byteToInt(buffer[21]);
        int xPort = Utility.twoBytesToInt(new byte[]{buffer[22], buffer[23]});
        int reflexivePort = computeReflexivePort(xPort);
        if(family == XorMappedAddress.IPv4_FAMILY) {
            byte[] xAddressBytes = new byte[4];
            System.arraycopy(buffer, 24, xAddressBytes, 0, 4);
            long xAddressValue = Utility.fourBytesToLong(xAddressBytes);
            InetAddress reflexiveAddress = computeReflexiveAddress(xAddressValue, family, transactionID);
            return new ReflexiveAddress(reflexiveAddress, reflexivePort);
        } else if(family == XorMappedAddress.IPv6_FAMILY) {
            byte[] xAddressBytes = new byte[16];
            System.arraycopy(buffer, 24, xAddressBytes, 0, 16);
            long xAddressValue = ByteBuffer.wrap(xAddressBytes).getLong();
            InetAddress reflexiveAddress = XorMappedAddress.computeReflexiveAddress(xAddressValue, family, transactionID);
            return new ReflexiveAddress(reflexiveAddress, reflexivePort);
        }
        return null;
    }
}
