package nat;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

import message.StunMessage;

/**
 * NAT that is connected to the private net.
 */
public class PrivateNAT {
    private int privatePort;
    private InetAddress privateAddress;
    private DatagramSocket socket;

    public PrivateNAT(int privatePort) throws SocketException {
        this.privatePort = privatePort;
        this.socket = new DatagramSocket(privatePort);
    }

    public void start() throws IOException {
        byte[] buffer = new byte[160];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        System.out.println(StunMessage.byteToString(packet.getData()));
        this.privatePort = packet.getPort();
        this.privateAddress = packet.getAddress();
        System.out.println(privateAddress);
        System.out.println(privatePort);

        int sendPort = 1252;
        buffer = "Sender til public nat".getBytes(StandardCharsets.UTF_8);
        packet = new DatagramPacket(buffer, buffer.length, privateAddress, sendPort);
        socket.send(packet);
    }

    public static void main(String[] args) throws IOException {
        PrivateNAT privateNAT = new PrivateNAT(1251);
        privateNAT.start();
    }

}
