import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

public class StunServer2 {
    private static final Logger LOGGER = Logger.getLogger(StunServer2.class.getName());
    private ICEServer iceServer;
    private DatagramSocket serverSocket;

    public StunServer2(ICEServer iceServer, DatagramSocket serverSocket) {
        this.iceServer = iceServer;
        this.serverSocket = serverSocket;
    }

    public StunServer2() {}

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
        StunServer2 server = new StunServer2();
        server.start();
    }

}
