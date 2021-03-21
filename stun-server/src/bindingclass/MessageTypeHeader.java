package bindingclass;

/**
 * The message type field is split into the class (request, response, error response or indication)
 * and the method.
 */
public class MessageTypeHeader {
    private enum MessageTypeClass {BindingRequest, BindingResponse, ErrorResponse, Indication};
    
}
