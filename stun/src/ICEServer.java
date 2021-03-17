public class ICEServer {
    //TODO: få en URL via hjelp.ntnu.no
    private String url = "stun:stun1.l.google.com:19302"; //Den url som ble brukt i eksemplet.
    private int port;
    private String serviceName = "stun"; //for UDP
    private String tlsServiceName ="stuns"; //for TLS
    private int defaultPort = 3478; //Default port for STUN requests is 3478 for UDP and TCP. Burde bruke denne porten
    //når man lager en egen STUN server.
    private int defaultPortTls = 5349;
}
