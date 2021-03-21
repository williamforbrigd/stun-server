package stunattributes;

import java.net.InetAddress;
import java.util.BitSet;

/**
 * Indicates a reflexive transport address of the client.
 */
public class MappedAddress extends StunAttribute {
    public enum AddressFamily {IPv4, IPv6};
    private int addressFamilyValue;
    private int port;
    private InetAddress inetAddress;
    private String address;

    public MappedAddress(AddressFamily addressFamily, int port, InetAddress inetAddress) {
        super();
        if(addressFamily == AddressFamily.IPv4) {
            addressFamilyValue = 0x01;
        } else if(addressFamily == AddressFamily.IPv6) {
            addressFamilyValue = 0x02;
        }
        this.port = port;
        this.inetAddress = inetAddress;
    }

    public String encodeToBinaryString() {
        return convertStringToBinary(address);
    }


    public String convertStringToBinary(String str) {
        StringBuilder res = new StringBuilder();
        char[] chars = str.toCharArray();
        for(char c : chars) {
            res.append(String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", 0));
        }
        return res.toString();
    }



    public static BitSet convert(long value) {
        BitSet bits = new BitSet();
        int index = 0;
        while (value != 0L) {
            if (value % 2L != 0) {
                bits.set(index);
            }
            ++index;
            value = value >>> 1;
        }
        return bits;
    }

    public static long convert(BitSet bits) {
        long value = 0L;
        for (int i = 0; i < bits.length(); ++i) {
            value += bits.get(i) ? (1L << i) : 0L;
        }
        return value;
    }

    public static void main(String[] args) {
        BitSet bitSet = new BitSet(8);
        bitSet.set(0,8);
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < bitSet.length(); i++) {
            sb.append(bitSet.get(i) ? 1 : 0);
        }
        System.out.println(sb);
        System.out.println(convert(bitSet));
    }
}
