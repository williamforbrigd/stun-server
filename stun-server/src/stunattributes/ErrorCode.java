package stunattributes;

import message.Utility;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * The ERROR-CODE attribute is used in error response messages.
 * See more information about error codes if needed.
 */
public class ErrorCode {
    private byte[] buffer;
    private int errorCode;
    private ErrorMessage errorMessage;
    private String reasonPhrase;

    public enum ErrorMessage {TryAlternate, BadRequest, Unauthorized, UnknownAttribute, StaleNonce, ServerError};

    public ErrorCode(int errorCode, String reasonPhrase) {
        this.errorCode = errorCode;
        buffer = new byte[8];
        buffer[0] = 0;
        buffer[1] = 0;
        buffer[2] = 0;
        buffer[3] = Utility.intToByte(errorCode);
        byte[] reasonBytes = reasonPhrase.getBytes(StandardCharsets.UTF_8);
        if(reasonBytes.length > 4) {
            System.out.println("The reason message is too long");
        } else {
            System.arraycopy(reasonBytes, 0, buffer, 4, 4);
        }
    }

    public ErrorCode(ErrorMessage errorMessage, String reasonPhrase) {
        this.errorMessage = errorMessage;
        this.reasonPhrase = reasonPhrase;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public ErrorMessage getErrorMessage() {
        return this.errorMessage;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public static ErrorMessage codeToMessage(int errorCode) {
        switch(errorCode) {
            case 300:
                return ErrorMessage.TryAlternate;
            case 400:
                return ErrorMessage.BadRequest;
            case 401:
                return ErrorMessage.Unauthorized;
            case 420:
                return ErrorMessage.UnknownAttribute;
            case 438:
                return ErrorMessage.StaleNonce;
            case 500:
                return ErrorMessage.ServerError;
        }
        return null;
    }

    public static ErrorCode parseErrorMessage(byte[] buffer) {
        ErrorMessage errorMessage = codeToMessage(Utility.byteToInt(buffer[3]));
        byte[] reasonPhraseBytes = new byte[4];
        System.arraycopy(buffer, 4, reasonPhraseBytes, 0, 4);
        String reasonPhrase = new String(reasonPhraseBytes);
        return new ErrorCode(errorMessage, reasonPhrase);
    }


}
