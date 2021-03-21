package udptest;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
    private DatagramSocket clientSocket;
    private InetAddress address;
    private int port = 1250;
    private byte[] buffer;
    private int bufferLength;

    public UDPClient(int bufferLength) throws SocketException, UnknownHostException {
        this.bufferLength = bufferLength;
        clientSocket = new DatagramSocket();
        address = InetAddress.getLocalHost(); //Get IP-address and port from the STUN server
    }

    public void runClient() throws IOException {
        buffer = new byte[this.bufferLength];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        clientSocket.send(packet);

        buffer = new byte[this.bufferLength];
        packet = new DatagramPacket(buffer, buffer.length);
        clientSocket.receive(packet);
        System.out.println(byteToStr(buffer));

        Scanner sc = new Scanner(System.in);
        String line = "";
        while(true) {
            line = sc.nextLine();
            buffer = line.getBytes();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            clientSocket.send(packet);
            if (line == null || line.equalsIgnoreCase("EXIT") || line.equals("")) {
                sc.close();
                clientSocket.close();
                return;
            }
            buffer = new byte[512];
            packet = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(packet);
            System.out.println(byteToStr(buffer));
        }
    }

    private static String byteToStr(byte[] buffer) {
        String str = "";
        int i=0;
        while(buffer[i] != 0) {
            str += (char)buffer[i];
            i++;
        }
        return str;
    }

    public static void main(String[] args) throws IOException {
        UDPClient client = new UDPClient(512);
        client.runClient();
    }

}
