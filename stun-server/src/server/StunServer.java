package server;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;


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
            }
        } catch(SocketException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        StunServer server = new StunServer(3478);
        server.start();
    }
}
