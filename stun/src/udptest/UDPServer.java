package udptest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    private DatagramSocket serverSocket;
    private InetAddress address;
    private int port = 1250;
    byte[] buffer;
    int bufferLength;

    public UDPServer(int bufferLength) throws SocketException {
        this.bufferLength = bufferLength;
        serverSocket= new DatagramSocket(port);
        buffer = new byte[bufferLength];
    }

    public void runServer() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        serverSocket.receive(packet);
        System.out.println("Client connected successfully");

        address = packet.getAddress();
        port = packet.getPort();
        buffer = "Enter in the format \"1 + 2\". Press enter to exit".getBytes();
        packet = new DatagramPacket(buffer, buffer.length, address, port);
        serverSocket.send(packet);

        while(true) {
            buffer = new byte[this.bufferLength];
            packet = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(packet);

            String received = byteToStr(buffer);
            System.out.println("Data received from client: " + received);
            if (received.equalsIgnoreCase("EXIT") || received.equals("") || received == null) {
                System.out.println("Exiting...");
                serverSocket.close();
                return;
            }
            String[] nums = received.split(" ");
            if (nums.length != 3) {
                buffer = "Please enter in a valid format.".getBytes();
                packet = new DatagramPacket(buffer, buffer.length, address, port);
                serverSocket.send(packet);
                continue;
            }
            long num1 = 0, num2 = 0;
            try {
                num1 = Long.parseLong(nums[0]);
                num2 = Long.parseLong(nums[2]);
            } catch (NumberFormatException e) {
                System.out.println("Could not convert from string to long: " + e.getMessage());
            }
            char operator = nums[1].charAt(0);
            String msg = "";
            if (operator == '+') {
                msg = num1 + " + " + num2 + " = " + (num1 + num2);
                buffer = msg.getBytes();
            } else if (operator == '-') {
                msg = num1 + " - " + num2 + " = " + (num1 - num2);
                buffer = msg.getBytes();
            }
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            serverSocket.send(packet);
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
        UDPServer server = new UDPServer(512);
        server.runServer();
    }
}
