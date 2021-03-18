package server;

import message.StunMessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class StunServer {
    private static final int PORT = 1252;
    private InetAddress address;
    private DatagramSocket socket;
    private int bufferLength;

    public StunServer(int bufferLength) throws SocketException {
        this.bufferLength = bufferLength;
        socket = new DatagramSocket(1252);
    }

    public void start() throws IOException {
        byte[] buffer = new byte[this.bufferLength];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        System.out.println(StunMessage.byteToString(packet.getData()));
    }

    public static void main(String[] args) throws IOException {
        StunServer server = new StunServer(StunMessage.BUFFER_LENGTH);
        server.start();
    }

}
