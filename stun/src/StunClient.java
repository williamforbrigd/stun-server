import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class StunClient {
    private DatagramSocket socketClient;
    private InetAddress address;
    private int port = 1250;
    private byte[] buffer;
    private int bufferLength;

    public StunClient(int bufferLength) throws SocketException, UnknownHostException {
        this.bufferLength = bufferLength;
        socketClient = new DatagramSocket();
        address = InetAddress.getLocalHost(); //Get IP-address and port from the STUN server
    }

    public void runClient() throws IOException {
        buffer = new byte[this.bufferLength];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socketClient.send(packet);

        buffer = new byte[this.bufferLength];
        packet = new DatagramPacket(buffer, buffer.length);
        socketClient.receive(packet);
        System.out.println(byteToStr(buffer));

        Scanner sc = new Scanner(System.in);
        String line = "";
        while(true) {
            line = sc.nextLine();
            buffer = line.getBytes();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
        }

    }

    private static String byteToStr(byte[] buffer) {
        String str = "";
        int i=0;
        while(buffer[i] != 0) {
            str += (char)buffer[i];
            i++;
        }
        return str;
    }

}
