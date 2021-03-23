package client;

import message.StunMessage;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * STUN client that is connected to the private network.
 * Does not know its public IP-address and port
 */
public class StunClient {
    private int privateClientPort;
    private InetAddress privateClientAddress;
    //These will eventually be set by the STUN server.
    private int publicPort;
    private InetAddress publicAddress;

    private DatagramSocket clientSocket;

    private String localDescription;
    private String remoteDescription;


    public StunClient(int privateClientPort) throws UnknownHostException {
        this.privateClientAddress = InetAddress.getLocalHost();
        this.privateClientPort = privateClientPort;
    }

    public int getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }

    public InetAddress getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(InetAddress publicAddress) {
        this.publicAddress = publicAddress;
    }

    public void setLocalDescription(String localDescription) {
        this.localDescription = localDescription;
    }

    public void setRemoteDescription(String remoteDescription) {
        this.remoteDescription = remoteDescription;

    }

    public void start() {
        try {
            clientSocket = new DatagramSocket(this.privateClientPort);
            DatagramPacket receive, send;
            try {
                StunMessage bindingRequest = new StunMessage(StunMessage.MessageClass.BINDING_REQUEST);
                byte[] buffer = bindingRequest.getHeader();
                send = new DatagramPacket(buffer, buffer.length, privateClientAddress, 1251);
                clientSocket.send(send);
            } catch(IOException e) {
                System.out.println("Could not send/receive packet: " + e.getMessage());
            }
        } catch(SocketException e) {
            System.out.println("Could not create client socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        StunClient client = new StunClient(1250);
        client.start();
    }
}
