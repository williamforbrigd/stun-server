package client;

import java.net.InetAddress;

public class ReflexiveAddress {
    private InetAddress reflexiveAddress;
    private int reflexivePort;

    public ReflexiveAddress(InetAddress reflexiveAddress, int reflexivePort) {
        this.reflexiveAddress = reflexiveAddress;
        this.reflexivePort = reflexivePort;
    }

    public ReflexiveAddress() {}

    public InetAddress getReflexiveAddress() {
        return reflexiveAddress;
    }

    public void setReflexiveAddress(InetAddress reflexiveAddress) {
        this.reflexiveAddress = reflexiveAddress;
    }

    public int getReflexivePort() {
        return reflexivePort;
    }

    public void setReflexivePort(int reflexivePort) {
        this.reflexivePort = reflexivePort;
    }

    public String toString() {
        return "Address: " + reflexiveAddress + "\n" + "Port: " + reflexivePort;
    }
}
