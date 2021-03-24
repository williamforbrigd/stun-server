package stunattributes;

import java.util.logging.Logger;

/**
 * The ERROR-CODE attribute is used in error response messages.
 * See more information about error codes if needed.
 */
public class ErrorCode {
    private static final Logger LOGGER = Logger.getLogger(ErrorCode.class.getName());
    private int errorCodeValue;
    private String errorMsg;

    public ErrorCode(int errorCodeValue, String errorMsg) {
        super();
        if(errorCodeValue < 300 || errorCodeValue > 699) {
            String msg = "Error code value is in the rang from 300 to 699";
            LOGGER.warning(msg);
            throw new IllegalArgumentException(msg);
        }
        this.errorCodeValue = errorCodeValue;
        this.errorMsg = errorMsg;
    }

}
