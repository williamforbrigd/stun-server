package message;

import client.ReflexiveAddress;
import jdk.jshell.execution.Util;
import stunattributes.XorMappedAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

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

    //The message length must contain the size in bytes of the message, not including the 20-byte STUN header.
    //All stun attributes are padded to a multiple of 4 bytes, the last 2 bits of this field is always zero.
    private int messageLength;
    public static final int MAGIC_COOKIE =  0x2112A442;
    private long transactionID;
    private MessageClass messageClass;

    //The total length of a stun message can be 576 bytes minus 20-byte IP header and minus 8-byte UDP header
    public static final int BUFFER_LENGTH = 576;
    private byte[] buffer;
    private byte[] header;

    public enum MessageClass {BINDING_REQUEST, SUCCESS_RESPONSE, ERROR_RESPONSE};

    public StunMessage(MessageClass messageClass, int messageLength) {
        this.messageClass = messageClass;
        this.messageLength = messageLength;
        this.buffer = new byte[BUFFER_LENGTH];
    }

    public StunMessage(MessageClass messageClass, int messageLength, long transactionID) {
        this.messageClass = messageClass;
        this.messageLength = messageLength;
        this.transactionID = transactionID;
    }

    public int getMagicCookie() {
        return this.MAGIC_COOKIE;
    }

    public MessageClass getMessageClass() {
        return this.messageClass;
    }

    public long getTransactionID() {
        return this.transactionID;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public byte[] createHeader() {
        header = new byte[20];
        byte[] messageType;
        if(this.messageClass == MessageClass.BINDING_REQUEST) {
            //binding request has class=0b00 (request) and method=0b000000000001 (binding)
            //it is encoded into the first 16 bits as 0x0001
            messageType = Utility.intToTwoBytes(0x0001);
            System.arraycopy(messageType, 0, header, 0,  2);
            this.messageLength = 0; //the message length for binding request is 0.
        } else if(this.messageClass == MessageClass.SUCCESS_RESPONSE) {
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
        System.arraycopy(id, 0, header, 8, 12);

        return header;
    }

    public byte[] generateTransactionID() {
        //the transaction id is 96 bits which is 12 bytes.
        byte[] id = new byte[12];
        for(int i=0; i < id.length; i++) {
            int rand = (int)(Math.random() * 256);
            id[i] = Utility.intToByte((int)(Math.random() * 256));
        }
        return id;
    }

    public static MessageClass findMessageClass(int messageType) {
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

    private static boolean checkTwoFirstBits(byte b) {
        String binary = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        if(binary.charAt(0) == '0' && binary.charAt(1) == '0')
            return true;
        else
            return false;
    }

    //TODO modify this if the header does not obey one of the rules for Section 6.
    public static StunMessage parseHeader(byte[] header) {
        byte[] messageType = new byte[2];
        System.arraycopy(header, 0, messageType, 0, 2);

        //TODO: Check that the two first bits are 0.
        if(!checkTwoFirstBits(messageType[0])) return null;

        int msgType = Utility.twoBytesToInt(messageType);
        MessageClass messageClass = findMessageClass(msgType);
        int count = 0;
        for(MessageClass c : MessageClass.values()) {
            if(c == messageClass)
                count++;
        }
        if(count == 0) return null; //Check that the messageclass matches one of the valid message classes.
        System.out.println("\nResult of parsing the header: ");
        System.out.println("The message class is: " + messageClass);

        byte[] msgLengthArr = new byte[2];
        System.arraycopy(header, 2, msgLengthArr, 0, 2);
        int msgLength = Utility.twoBytesToInt(msgLengthArr);
        System.out.println("The message length is: " + msgLength);

        byte[] magicCookieArr = new byte[4];
        System.arraycopy(header, 4, magicCookieArr, 0, 4);
        long magicCookie = Utility.fourBytesToLong(magicCookieArr);
        if(magicCookie == StunMessage.MAGIC_COOKIE) {
            System.out.println("The magic cookie is correct");
        }

        byte[] transactIdArr = new byte[12];
        System.arraycopy(header, 8, transactIdArr, 0, 12);
        long transactionId = 1;
        for(byte b : transactIdArr) {
            transactionId *= Utility.byteToInt(b);
        }
        System.out.println("The transaction id is: " + transactionId);

        return new StunMessage(messageClass, msgLength, transactionId);
    }
}
