package nat;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * NAT that is connceted to the public internet.
 */
public class PublicNAT {
    private DatagramSocket socket;
    private InetAddress publicAddress;
    private int publicPort;
    private int bufferLength;
    private int privatePort;

    public PublicNAT(int bufferLength, int privatePort) throws SocketException, UnknownHostException {
        this.privatePort = privatePort;
        this.bufferLength = bufferLength;
        this.socket = new DatagramSocket(privatePort);
        this.publicAddress = InetAddress.getLocalHost();
    }

    public void start() throws IOException {
        byte[] buffer = new byte[this.bufferLength];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        System.out.println(StunMessage.byteToString(packet.getData()));
        System.out.println(packet.getAddress());
        System.out.println(packet.getPort());

        buffer = "sender til Stun Serveren".getBytes(StandardCharsets.UTF_8);
        int sendPort = 3478;
        packet = new DatagramPacket(buffer, buffer.length, publicAddress, sendPort);
        socket.send(packet);
    }

    public static void main(String[] args) throws IOException {
        PublicNAT publicNAT = new PublicNAT(160, 1252);
        publicNAT.start();
    }
}
