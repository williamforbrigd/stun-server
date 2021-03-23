package stunattributes;

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
public class XorMappedAddress extends StunAttribute {
    private int family;
    private int xPort;
    private String xAddress;
    private byte[] buffer;
    private int bufferLength;

    public XorMappedAddress(int family, int mappedPort, String mappedAddress, int transactionID) {
        super();
        this.family = family;
        this.xPort = xPort;
        this.xAddress = xAddress;

        if(family == 0x01) {
            //The address is IPv4 and is 32 bits.
            //32 bits + 32 bits for address = 64 bits which is 8 bytes
            bufferLength = 8;
        } else if(family == 0x02) {
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

    public byte[] getBuffer() {
        return this.buffer;
    }

    /**
     * X-port is computed by taking the mapped port in host byte order, XOR'ing it with the most signifikant
     * bit of the magic cookie, and then converting the result to network byte order.
     * I assume the mapped port is already in host byte order.
     */
    private byte[] computeXPortBytes(int mappedPort) {
        byte[] magicCookieBytes = Utility.intToFourBytes(StunMessage.MAGIC_COOKIE);
        //Most significant bits of the magic cookie are the two first bytes.
        int mostSign = Utility.twoBytesToInt(new byte[]{magicCookieBytes[0], magicCookieBytes[1]});
        this.xPort = mappedPort ^ mostSign;
        //Then convert to network byte order.
        //Network byte order uses always big endian.
        return ByteBuffer.allocate(2).putInt(xPort).order(ByteOrder.BIG_ENDIAN).array();
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
    private byte[] computeXAddressBytes(String mappedAddress, int transactionID) {
        byte[] bytes = null;
        try {
            InetAddress ip = InetAddress.getByName(mappedAddress);
            bytes = ip.getAddress(); //assuming the bytes are received in host byte order.
        } catch(UnknownHostException e) {
            System.out.println("could not get ip from name: " + e.getMessage());
        }
        long addressValue = 0, mapped = 0;
        if(this.family == 0x01 && bytes != null) {
            //IPv4 address is 4 bytes and magic cookie is 4 bytes
            mapped = Utility.fourBytesToLong(bytes);
            addressValue = mapped ^ StunMessage.MAGIC_COOKIE;

            return ByteBuffer.allocate(4).putLong(addressValue).array();
        } else if(this.family == 0x02 && bytes != null) {
            //IPv6 address is 16 bytes
            //Magic cookie is 4 bytes. Transaction id is 12 bytes. cookie + id = 16 bytes
            mapped = ByteBuffer.wrap(bytes).getInt();
            addressValue = mapped ^ (StunMessage.MAGIC_COOKIE + transactionID);

            return ByteBuffer.allocate(16).putLong(addressValue).array();
        }
        return null;
    }
    /**
     * The XOR-MAPPED-ADDRESS encodes the transport address by xor'ing it with the magic cookie.
     * @param magicCookie
     * @return
     */
    public String encodeAddressToBinary(int magicCookie) {
        String binary = convertStringToBinary(xAddress);
        int addToBinary = Integer.parseInt(binary, 2);
        int res = addToBinary ^ magicCookie;

        //TODO: change this
        return new String();
    }

    public String convertStringToBinary(String str) {
        StringBuilder res = new StringBuilder();
        char[] chars = str.toCharArray();
        for(char c : chars) {
            res.append(String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", "0"));
        }
        return res.toString();
    }
}
