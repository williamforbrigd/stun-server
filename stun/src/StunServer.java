import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Logger;

public class StunServer {
    private static final Logger LOGGER = Logger.getLogger(StunServer.class.getName());
    private ICEServer iceServer;
    private DatagramSocket serverSocket;

    public StunServer(ICEServer iceServer, DatagramSocket serverSocket) {
        this.iceServer = iceServer;
        this.serverSocket = serverSocket;
    }

    public StunServer() {}

    public void start() {
        while(true) {
            try {
                byte[] buffer = new byte[576];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);
                System.out.println(receivePacket.getData());
            } catch(IOException e) {
                String msg = "Could not receive packet: " + e.getMessage();
                LOGGER.warning(msg);
                System.out.println(msg);
            }
        }
    }

    public static void main(String[] args) {
        StunServer server = new StunServer();
        server.start();
    }

}
