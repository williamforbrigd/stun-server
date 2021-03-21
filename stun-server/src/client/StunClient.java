package client;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * STUN client that is connected to the private network.
 * Does not know its public IP-address and port
 */
public class StunClient {
    private int privatePort;
    private InetAddress privateAddress;
    private DatagramSocket socket;

    private String localDescription;
    private String remoteDescription;

    //These will eventually be set by the STUN server.
    private int publicPort;
    private InetAddress publicAddress;

    public StunClient(int privatePort) throws UnknownHostException, SocketException {
        this.privatePort = privatePort;
        this.socket = new DatagramSocket(this.privatePort);
        this.privateAddress = InetAddress.getLocalHost();
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

    public void start() throws IOException {
        System.out.println(privateAddress);
        System.out.println(privatePort);
        byte[] buffer = "hei hva skjer".getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, privateAddress, 1251);
        socket.send(packet);
    }

    public String formBindingRequest() {
        return "";
    }


    public static void main(String[] args) throws IOException {
        StunClient client = new StunClient(1250);
        client.start();
    }

}
