package nat;

import java.io.IOException;
import java.net.*;

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

    public void start() {
        try {
            socket = new DatagramSocket(1251);
            DatagramPacket send, receive;
            try {
                byte[] buffer = new byte[bufferLength];
                receive = new DatagramPacket(buffer, buffer.length);
                socket.receive(receive);
                System.out.println("Received from client with adress: " + receive.getAddress() + " and port: " + receive.getPort());
                InetAddress privateClientAddress = receive.getAddress();
                int privateClientPort = receive.getPort();

                send = new DatagramPacket(buffer, buffer.length, privateNatAddress, 1252);
                socket.send(send);

                receive = new DatagramPacket(buffer, buffer.length);
                socket.receive(receive);

                send = new DatagramPacket(buffer, buffer.length, privateClientAddress, privateClientPort);
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
