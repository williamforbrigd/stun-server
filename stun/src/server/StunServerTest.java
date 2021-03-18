package server;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class StunServerTest {
    InetAddress address;
    int port;

    public StunServerTest(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public StunServerTest() {}

    public void run() throws IOException {
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        NetworkInterface iface = ifaces.nextElement();
        System.out.println(iface.getInetAddresses());
        Enumeration<InetAddress> iaddresses = iface.getInetAddresses();
        InetAddress address = iaddresses.nextElement();

        DatagramSocket socket = new DatagramSocket(new InetSocketAddress(address, 0));
        socket.setReuseAddress(true);
        socket.connect(InetAddress.getByName("jstun.javawi.de"), 3478);

        byte[] buffer = new byte[160];
        buffer = "testing".getBytes(StandardCharsets.UTF_8);
        DatagramPacket send = new DatagramPacket(buffer, buffer.length);
        socket.send(send);

        System.out.println("Testing to send request");

        DatagramPacket receive = new DatagramPacket(new byte[200], 200);
        socket.receive(receive);
        System.out.println(StunMessage.byteToString(receive.getData()));
    }

    public static void main(String[] args) throws IOException {
        StunServerTest test = new StunServerTest();
        test.run();
    }
}
