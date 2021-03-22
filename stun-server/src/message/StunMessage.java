package message;

import stunattributes.StunAttribute;

/**
 * Class for message.StunMessage
 *
 *
 *  0                   1                   2                   3
 *        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |0 0|     STUN Message Type     |         Message Length        |
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |                         Magic Cookie                          |
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *       |                                                               |
 *       |                     Transaction ID (96 bits)                  |
 *       |                                                               |
 *       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */


public class StunMessage {
    private int messageType;
    private int messageLength;
    private int magicCookie;
    private int transactionID;
    private MessageTypeClass messageTypeClass;

    public static final int BUFFER_LENGTH = 548;
    private byte[] header;

    public enum MessageTypeClass {BINDING_REQUEST, INDICATION, SUCCESS_RESPONSE, ERROR_RESPONSE};


    public StunMessage(MessageTypeClass messageTypeClass) {
        this.messageTypeClass = messageTypeClass;
        header = new byte[20];
        this.magicCookie = 0x2112A442;
    }

    public StunMessage() {}

    public MessageTypeClass getMessageTypeClass() {
        return this.messageTypeClass;
    }

    public byte[] getHeader() {
        return this.header;
    }

    public int getMagicCookie() {
        return this.magicCookie;
    }

    public MessageTypeClass findMessageType(int messageType) {
        if((messageType & 0x0110) == 0x0000) return MessageTypeClass.BINDING_REQUEST;
        else if((messageType & 0x0110) == 0x0010) return MessageTypeClass.INDICATION;
        else if((messageType & 0x0110) == 0x0100) return MessageTypeClass.SUCCESS_RESPONSE;
        else if((messageType & 0x0110) == 0x0110) return MessageTypeClass.ERROR_RESPONSE;
        else return null;
    }

    /**
     * Interval is from 0 to 2**96-1
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public static String byteToString(byte[] buffer) {
        String str = "";
        int i=0;
        while(buffer[i] != 0) {
            str += (char)buffer[i];
            i++;
        }
        return str;
    }

    public byte[] generateTransactionID() {
        //the transaction id is 96 bits which is 12 bytes.
        byte[] id = new byte[12];
        for(int i=0; i < id.length; i++) {
            id[i] = Utility.intToByte((int)(Math.random() * 256));
        }
        return id;
    }

    public int messageTypeToInt(MessageTypeClass messageTypeClass) {
        int res = 0;
        if(messageTypeClass == MessageTypeClass.BINDING_REQUEST) res = 0b01;
        else if(messageTypeClass == MessageTypeClass.SUCCESS_RESPONSE) res = 0b10;
        else if(messageTypeClass == MessageTypeClass.ERROR_RESPONSE) res = 0b11;
        return res;
    }

    //TODO: parse the bytes from a buffer and get the message type
    public static StunMessage checkMessage(byte[] buffer) {
        return new StunMessage(MessageTypeClass.BINDING_REQUEST);
    }

    public static void main(String[] args) {
        System.out.println(2 >> 1);
        System.out.println(2 >> 2);
        System.out.println(2 >> 3);
        System.out.println(2 << 1);
        System.out.println(2 << 2);
        System.out.println(2 << 3);
        int num = Integer.parseInt("1011", 2);
        System.out.println(num >> 1);
        System.out.println(num);
        System.out.println(Integer.parseInt("1011", 2));
    }
}
