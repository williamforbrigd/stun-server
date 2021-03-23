package nat;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * NAT that is connceted to the public internet.
 */
public class PublicNAT {
    private InetAddress publicNatAddress;
    private int publicNatPort;
    private DatagramSocket socket;
    private static int bufferLength = StunMessage.BUFFER_LENGTH;

    public PublicNAT(int publicNatPort, String hostName) throws UnknownHostException {
        this.publicNatPort = publicNatPort;
        //this.publicNatAddress = InetAddress.getByName(hostName);
        this.publicNatAddress = InetAddress.getLocalHost();
    }

    public void start() throws IOException {
        try {
            socket = new DatagramSocket(1252);
            DatagramPacket send, receive;
            try {
                byte[] buffer = new byte[bufferLength];
                receive = new DatagramPacket(buffer, buffer.length);
                socket.receive(receive);
                System.out.println("From private nat: " + new String(receive.getData(), 0, receive.getLength()));

                buffer = "sending to stun server".getBytes(StandardCharsets.UTF_8);
                send = new DatagramPacket(buffer, buffer.length, publicNatAddress, 3478);
                socket.send(send);
            } catch(IOException e) {
                System.out.println("Could not send/receive packet: " + e.getMessage());
            }
        } catch(SocketException e) {
            System.out.println("Could not create socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        PublicNAT publicNAT = new PublicNAT(1252, "");
        publicNAT.start();
    }
}
