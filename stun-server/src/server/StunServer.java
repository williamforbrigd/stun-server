package server;

import message.StunMessage;
import stunattributes.XorMappedAddress;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class StunServer {
    private InetAddress address;
    private DatagramSocket socket;
    private int bufferLength;
    private byte[] buffer;
    private ICEServer iceServer;

    public StunServer(int bufferLength, String stunServer, int defaultPort) throws SocketException {
        this.bufferLength = bufferLength;
        socket = new DatagramSocket();
        iceServer = new ICEServer(stunServer, defaultPort);
    }

    public void start() throws IOException {
        socket.connect(InetAddress.getByName(this.iceServer.getStunServer()), this.iceServer.getDefaultPort());
        //Receiving a STUN message
        buffer = new byte[this.bufferLength];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        StunMessage msg = StunMessage.checkMessage(packet.getData());
        System.out.println(StunMessage.byteToString(packet.getData()));

        if(msg.getMessageTypeClass() == StunMessage.MessageTypeClass.BINDING_REQUEST) {
            XorMappedAddress xorMappedAddress = new XorMappedAddress();

        }
    }

    public static void main(String[] args) throws IOException {
        //The default port for TCP and UDP are 3478
        StunServer server = new StunServer(StunMessage.BUFFER_LENGTH, "jstun.javawi.de", 3478);
        //StunServer server = new StunServer(StunMessage.BUFFER_LENGTH,"stun1.l.google.com", 3478);
        server.start();

    }

}
