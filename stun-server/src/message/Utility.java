package message;

public class Utility {

    public static byte intToByte(int value) {
        return (byte)(value & 0xff);
    }

    public static byte[] intToTwoBytes(int value) {
        byte[] res = new byte[2];
        res[0] = (byte)((value >>> 8) & 0xFF);
        res[1] = (byte)(value & 0xFF);
        return res;
    }

}
