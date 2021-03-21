package stunattributes;

public class StunAttribute {
    private int type;
    private int length;
    private int value;

    /**
     * 0                   1                   2                   3
     *        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *       |         Type                  |            Length             |
     *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     *       |                         Value (variable)                ....
     *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     */
    public StunAttribute(int type, int length, int value) {
        this.type = type;
        this.length = length;
        this.value = value;
    }

    public StunAttribute() {}
}
