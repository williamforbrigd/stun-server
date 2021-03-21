package stunattributes;


import message.StunMessage;

/**
 * 0                   1                   2                   3
 *        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |         Type                  |            Length             |
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |                         Value (variable)                ....
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */
public class StunAttribute {
    private int type;
    private int length;
    private int value;

    private final static int MAPPED_ADDRESS = 0x0001;
    private final static int ERROR_CODE = 0x0009;
    private final static int XOR_MAPPED_ADDRESS = 0x0020;

    public enum StunAttributeType {MAPPED_ADDRESS, ERROR_CODE, XOR_MAPPED_ADDRESS};

    public StunAttribute(int type, int length, int value) {
        this.type = type;
        this.length = length;
        this.value = value;
    }

    public StunAttribute() {}

    public int typeToInteger(StunAttributeType type) {
        if(type == StunAttributeType.MAPPED_ADDRESS) return this.MAPPED_ADDRESS;
        else if(type == StunAttributeType.ERROR_CODE) return this.ERROR_CODE;
        else if (type == StunAttributeType.XOR_MAPPED_ADDRESS) return this.XOR_MAPPED_ADDRESS;
        else return -1;
    }

    public StunAttributeType intToType(int type) {
        if(type == 0x0001) return StunAttributeType.MAPPED_ADDRESS;
        else if(type == 0x0009) return StunAttributeType.ERROR_CODE;
        else if(type == 0x0020) return StunAttributeType.XOR_MAPPED_ADDRESS;
        else return null;
    }
}
