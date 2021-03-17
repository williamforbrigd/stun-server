import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class StunServer {
    private DatagramSocket serverSocket;
    private InetAddress address;
    private int port = 1250;
    byte[] buffer;

    public StunServer(int bufferLength) throws SocketException {
        serverSocket= new DatagramSocket(port);
        buffer = new byte[bufferLength];
    }

    public void runServer() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        serverSocket.receive(packet);
        System.out.println("Client connected successfully");

        address = packet.getAddress();
        port = packet.getPort();
        buffer = "Enter in the format \"1 + 2\". Press enter to exit".getBytes();
        packet = new DatagramPacket(buffer, buffer.length, address, port);
        serverSocket.send(packet);

    }
}
