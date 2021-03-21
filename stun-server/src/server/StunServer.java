package server;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class StunServer {
    private InetAddress address;
    private DatagramSocket socket;
    private int bufferLength;
    private byte[] buffer;
    private int port;

    public StunServer(int bufferLength, int port) throws SocketException {
        this.bufferLength = bufferLength;
        this.port = port;
        socket = new DatagramSocket(this.port);
    }

    public StunServer() {}

    public void start() throws IOException {
        byte[] buffer = new byte[this.bufferLength];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        System.out.println(StunMessage.byteToString(buffer));
        System.out.println(packet.getAddress());
        System.out.println(packet.getPort());
    }

    public void runTestServer() throws IOException {
        //this.socket = new DatagramSocket(new InetSocketAddress());
        socket.connect(InetAddress.getByName("jstun.javawi.de"), 3478);
        socket.setReuseAddress(true);

        byte[] buffer = "testing to send packet".getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.send(packet);

        packet = new DatagramPacket(new byte[200], 200);
        socket.receive(packet);
        System.out.println(new String(packet.getData(), 0, packet.getLength()));

    }

    public static void main(String[] args) throws IOException {
        //StunServer server = new StunServer(StunMessage.BUFFER_LENGTH, 1253);
        //server.start();
        StunServer server = new StunServer();
        server.runTestServer();
    }
}
