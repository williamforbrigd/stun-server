package stunattributes;

import java.net.InetAddress;

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


    public XorMappedAddress(int family, int xPort, String xAddress) {
        super();
        this.family = family;
        this.xPort = xPort;
        this.xAddress = xAddress;
    }

    public byte[] bytesFromAddress() {

        //TODO finish this
        return new byte[200];
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
