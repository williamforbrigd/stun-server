package server;

/**
 * Interactive Connectivity Establishment
 */
public class ICEServer {
    //TODO get an url from hjelp.ntnu.no
    private String stunServer;
    private int defaultPort;

    public ICEServer(String stunServer, int defaultPort) {
        this.stunServer = stunServer;
        this.defaultPort = defaultPort;
    }

    public String getStunServer() {
        return this.stunServer;
    }

    public int getDefaultPort() {
        return this.defaultPort;
    }
}
