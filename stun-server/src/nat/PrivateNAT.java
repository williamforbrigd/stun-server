package nat;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

import message.StunMessage;

/**
 * NAT that is connected to the private net.
 */
public class PrivateNAT {
    private InetAddress privateNatAddress;
    private int privateNatPort;
    private DatagramSocket socket;
    private static int bufferLength = StunMessage.BUFFER_LENGTH;

    public PrivateNAT(int privateNatPort) throws UnknownHostException {
        this.privateNatPort = privateNatPort;
        this.privateNatAddress = InetAddress.getLocalHost();
    }

    public void start() throws IOException {
        try {
            socket = new DatagramSocket(1251);
            DatagramPacket send, receive;
            try {
                byte[] buffer = new byte[bufferLength];
                receive = new DatagramPacket(buffer, buffer.length);
                socket.receive(receive);
                System.out.println("Client msg: " + new String(receive.getData(), 0, receive.getLength()));

                buffer = "Sending to public nat".getBytes(StandardCharsets.UTF_8);
                send = new DatagramPacket(buffer, buffer.length, privateNatAddress, 1252);
                socket.send(send);
            } catch(IOException e) {
                System.out.println("Could not send/reveice packet: " + e.getMessage());
            }
        } catch(SocketException e) {
            System.out.println("Could not create socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        PrivateNAT privateNAT = new PrivateNAT(1251);
        privateNAT.start();
    }

}
