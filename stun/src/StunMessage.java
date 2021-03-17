import java.math.BigInteger;

/**
 * Class for StunMessage
 */
public class StunMessage {
    private int messageType;
    private int messageLength;
    private int magicCookie;
    private int transactionID;
    private MessageTypeClass messageTypeClass;


    private enum MessageTypeClass {BINDING_REQUEST, INDICATION, SUCCESS_RESPONSE, ERROR_RESPONSE};

    /**
     * TODO: check that the 2 first bytes are 0 and also check that the other attributes are of correct byte length.
     * @param messageType
     * @param messageLength
     */
    public StunMessage(int messageType, int messageLength) {
        this.messageType = messageType;
        this.messageTypeClass = findMessageType(messageType);
        this.messageLength = messageLength;
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
}
