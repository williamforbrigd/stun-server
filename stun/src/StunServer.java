import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Logger;

public class StunServer {
    private ICEServer iceServer;
    private static final Logger LOGGER = Logger.getLogger(StunServer.class.getName());

    public StunServer(ICEServer iceServer) {
        this.iceServer = iceServer;
    }

    public void start() {
        //The client must first form a request to the server to identify the IP-address and the port.
        //After that the client must form a subsequent request once a previous request/response transaction has been
        //completed successfully.

        //First request
            //DNS discovery
        //is subsequent request necessary?

        while(true) {
            try {
                DatagramSocket receive = new DatagramSocket();
            } catch(SocketException e) {
                String msg = "Could not create socket: ";
                System.out.println(msg + e.getMessage());
                LOGGER.warning(msg + e.getMessage());
            }
        }
    }

    public void bindingRequest() {}

}
