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

    public static byte[] intToFourBytes(int value) {
        byte[] res = new byte[4];
        res[0] = (byte)((value >>> 24) & 0xFF);
        res[1] = (byte)((value >>> 16) & 0xFF);
        res[2] = (byte)((value >>> 8) & 0xFF);
        res[3] = (byte)(value & 0xFF);
        return res;
    }

    public static int byteToInt(byte value) {
        return (int)value & 0xff;
    }

    public static int twoBytesToInt(byte[] value) {
        int tmp1 = value[0] & 0xff;
        int tmp2 = value[1] & 0xff;
        return ((tmp1 << 8) + tmp2);
    }

    public static long fourBytesToLong(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return (((long)temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }
}
