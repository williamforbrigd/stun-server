package server;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

//TODO bruke datagramsocket eller ServerSocket
public class StunServer {
    private DatagramSocket serverSocket;
    private static int bufferLength = StunMessage.BUFFER_LENGTH;
    private byte[] buffer;
    private InetAddress serverAddress;
    private InetAddress clientAddress;
    private int serverPort;
    private int clientPort;

    public StunServer(int serverPort) throws SocketException {
        this.serverPort = serverPort;
    }

<<<<<<< HEAD
    public void start() {
        try {
            serverSocket = new DatagramSocket(this.serverPort);
            DatagramPacket receive, send;
            try {
                buffer = new byte[this.bufferLength];
                receive = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receive);
                clientAddress = receive.getAddress();
                clientPort = receive.getPort();

                System.out.println("Msg from client: " + new String(receive.getData(), 0, receive.getLength()));
                System.out.println("Server address: " + serverSocket.getInetAddress());
                System.out.println("Server port: " + serverPort);
                System.out.println("Client address: " + clientAddress);
                System.out.println("Client port: " + clientPort);

                //TODO form a Binding response

            } catch(IOException e) {
                e.printStackTrace();
=======
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
>>>>>>> 7144bc4142925a71faf0fa38f40307f5656086a7
            }
        } catch(SocketException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public static void main(String[] args) throws IOException {
        StunServer server = new StunServer(3478);
=======
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        StunServer server = new StunServer(StunMessage.BUFFER_LENGTH, 3478);
>>>>>>> 7144bc4142925a71faf0fa38f40307f5656086a7
        server.start();
    }
}
