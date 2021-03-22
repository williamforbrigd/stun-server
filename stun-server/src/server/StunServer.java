package server;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

//TODO bruke datagramsocket eller ServerSocket
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

        byte[] data = packet.getData();
        for(int i=3; i < 16; i++) {
            System.out.println(data[i]);
        }

        System.out.println(packet.getData().length);
        System.out.println(StunMessage.byteToString(buffer));
        System.out.println(packet.getAddress());
        System.out.println(packet.getPort());
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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        StunServer server = new StunServer(StunMessage.BUFFER_LENGTH, 3478);
        server.start();
    }
}
