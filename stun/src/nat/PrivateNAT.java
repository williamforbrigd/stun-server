package nat;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

import message.StunMessage;

/**
 * NAT that is connected to the private net.
 */
public class PrivateNAT {
    private int privatePort = 1250;
    private InetAddress privateAddress;
    private int messageLength = 160;
    private DatagramSocket socket;

    public PrivateNAT() throws SocketException {
        this.socket = new DatagramSocket(privatePort);
    }

    public void start() throws IOException {
        byte[] buffer = new byte[messageLength];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        System.out.println(StunMessage.byteToString(packet.getData()));
        this.privatePort = packet.getPort();
        this.privateAddress = packet.getAddress();
        System.out.println(privateAddress);
        System.out.println("Success");

        int sendPort = 1251;
        buffer = "Sender til public nat".getBytes(StandardCharsets.UTF_8);
        packet = new DatagramPacket(buffer, buffer.length, privateAddress, sendPort);
        socket.send(packet);
    }

    public static void main(String[] args) throws IOException {
        PrivateNAT privateNAT = new PrivateNAT();
        privateNAT.start();
    }

}
