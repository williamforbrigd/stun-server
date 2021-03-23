package client;

import message.StunMessage;
import message.Utility;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

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

        //Send binding request
        StunMessage bindingRequest = new StunMessage(StunMessage.MessageTypeClass.BINDING_REQUEST);
        int typeInt = bindingRequest.messageTypeToInt(bindingRequest.getMessageTypeClass());
        byte typeByte = Utility.intToByte(typeInt);
        System.out.println("the type is: " + typeByte);
        bindingRequest.getHeader()[3] = typeByte;
        int cookie = bindingRequest.getMagicCookie();
        byte cookieType = Utility.intToByte(cookie);
        bindingRequest.getHeader()[4] = cookieType;
        byte[] id = bindingRequest.generateTransactionID();
        System.arraycopy(id, 0, bindingRequest.getHeader(), 0, id.length);

        byte[] buffer = bindingRequest.getHeader();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, privateAddress, 3478);
        socket.send(packet);
    }

    public String formBindingRequest() {
        return "";
    }


    public static void main(String[] args) throws IOException {
        System.out.println(Utility.intToByte(0x2112A442));
        System.out.println(Utility.intToByte(0b01));
        StunClient client = new StunClient(1250);
        client.start();
    }
}
