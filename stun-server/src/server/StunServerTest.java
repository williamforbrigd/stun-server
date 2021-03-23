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

    public void runTestServer() throws IOException, ClassNotFoundException {
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while(ifaces.hasMoreElements()) {
            NetworkInterface iface = ifaces.nextElement();
            Enumeration<InetAddress> iaddresses = iface.getInetAddresses();
            while(iaddresses.hasMoreElements()) {
                InetAddress address = iaddresses.nextElement();
                if (Class.forName("java.net.Inet4Address").isInstance(address)) {
                    if ((!address.isLoopbackAddress()) && (!address.isLinkLocalAddress())) {
                        System.out.println(address);
                        DatagramSocket socket = new DatagramSocket(new InetSocketAddress(address, 0));
                        socket.setReuseAddress(true);
                        //socket.connect(InetAddress.getByName("jstun.javawi.de"), 3478);
                        socket.connect(InetAddress.getByName("stun1.l.google.com"), 3478);
                        //socket.setSoTimeout(300);
                        System.out.println(socket.getInetAddress().getHostAddress());
                        System.out.println(socket.getInetAddress().getHostName());
                        System.out.println(socket.getPort());

                        byte[] buffer = "Hei hva skjer".getBytes(StandardCharsets.UTF_8);
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.send(packet);

                        DatagramPacket receive = new DatagramPacket(new byte[300], 300);
                        socket.receive(receive);
                        System.out.println(new String(receive.getData(), 0, receive.getLength()));
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        StunServerTest test = new StunServerTest();
        test.run();
    }
}
