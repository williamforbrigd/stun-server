package message;

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
    private static final int MAGIC_COOKIE =  0x2112A442;
    private int transactionID;
    private MessageClass messageClass;

    public static final int BUFFER_LENGTH = 548;
    private byte[] header;

    public enum MessageClass {BINDING_REQUEST, SUCCESS_RESPONSE, ERROR_RESPONSE};

    public StunMessage(MessageClass messageClass) {
        this.messageClass = messageClass;
        header = new byte[20];
        byte[] messageType = new byte[2];
        if(messageClass == MessageClass.BINDING_REQUEST) {
            //binding request has class=0b00 (request) and method=0b000000000001 (binding)
            //it is encoded into the first 16 bits as 0x0001
            messageType = Utility.intToTwoBytes(0x0001);
            System.arraycopy(messageType, 0, header, 0,  2);
            this.messageLength = 0; //the message length for binding request is 0.
        } else if(messageClass == MessageClass.SUCCESS_RESPONSE) {
            //Binding response has class=0b10  (sucess response) and method=0b000000000001
            //it is encoded into the first 16 bits as 0x0101
            messageType = Utility.intToTwoBytes(0x0101);
            System.arraycopy(messageType, 0, header, 0, 2);
        }
        //the message length takes the 2 next bytes. Message length for request is 0.
        byte[] msgLength = Utility.intToTwoBytes(messageLength);
        System.arraycopy(msgLength, 0, header, 2, 2);
        //The magic cookie takes the next 4 bytes.
        byte[] cookieBytes = Utility.intToFourBytes(MAGIC_COOKIE);
        System.arraycopy(cookieBytes, 0, header, 4, 4);
        //The transaction id takes the next 12 bytes.
        byte[] id = generateTransactionID();
        System.arraycopy(id, 0, header, 7, 12);
    }

    public byte[] getHeader() {
        return this.header;
    }

    public int getMagicCookie() {
        return this.MAGIC_COOKIE;
    }

    public byte[] generateTransactionID() {
        //the transaction id is 96 bits which is 12 bytes.
        byte[] id = new byte[12];
        for(int i=0; i < id.length; i++) {
            id[i] = Utility.intToByte((int)(Math.random() * 256));
        }
        return id;
    }

    public MessageClass findMessageClass(int messageType) {
        if((messageType & 0x0110) == 0x0000) return MessageClass.BINDING_REQUEST;
        else if((messageType & 0x0110) == 0x0100) return MessageClass.SUCCESS_RESPONSE;
        else if((messageType & 0x0110) == 0x0110) return MessageClass.ERROR_RESPONSE;
        else return null;
    }

    public int messageTypeToInt(MessageClass messageClass) {
        int res = 0;
        if(messageClass == MessageClass.BINDING_REQUEST) res = 0b00;
        else if(messageClass == MessageClass.SUCCESS_RESPONSE) res = 0b10;
        else if(messageClass == MessageClass.ERROR_RESPONSE) res = 0b11;
        return res;
    }
}
