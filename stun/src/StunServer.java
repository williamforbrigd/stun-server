public class StunServer {
    private ICEServer ice;

    public StunServer(ICEServer ice) {
        this.ice = ice;
    }

    public void start() {
        //The client must first form a request to the server to identify the IP-address and the port.
        //After that the client must form a subsequent request once a previous request/response transaction has been
        //completed successfully.

        //First request
            //DNS discovery
        //is subsequent request necessary?
    }

    public void bindingRequest() {}

}
