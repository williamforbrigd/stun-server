package server;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * The reflexive transport address is the public IP address and port created by the NAT closest to the server.
 * The reflexive transport address is created by the PublicNat
 */
public class StunServer {
    private InetAddress serverAddress;
    private int serverPort;
    private InetAddress reflexiveAddress;
    private int reflexivePort;
    private DatagramSocket socket;
    private static int bufferLength = StunMessage.BUFFER_LENGTH;

    public StunServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            socket = new DatagramSocket(this.serverPort);
            DatagramPacket receive, send;
            System.out.println("Server address: " + socket.getInetAddress());
            System.out.println("Server port: " + serverPort);
            try {
                byte[] buffer = new byte[bufferLength];
                receive = new DatagramPacket(buffer, buffer.length);
                socket.receive(receive);
                reflexiveAddress = receive.getAddress();
                reflexivePort = receive.getPort();

                System.out.println("Reflexive transport address: " + reflexiveAddress);
                System.out.println("Reflexive port: " + reflexivePort);

                //TODO form a Binding response
                StunMessage message = StunMessage.parseHeader(buffer);
                if(message.getMessageClass() == StunMessage.MessageClass.BINDING_REQUEST) {
                    StunMessage response = new StunMessage(StunMessage.MessageClass.SUCCESS_RESPONSE, 0);
                }

            } catch (IOException e) {
                System.out.println("Could not send/receive packet: " + e.getMessage());
            }
        } catch (SocketException e) {
            System.out.println("Could not create socket: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        StunServer server = new StunServer(3478);
        server.start();
    }

}


