/**
 * Class that folows the session description protocoll (SDP)
 *
 *
 * Session description
 *     v=  (protocol version number, currently only 0)
 *     o=  (originator and session identifier : username, id, version number, network address)
 *     s=  (session name : mandatory with at least one UTF-8-encoded character)
 *     i=* (session title or short information)
 *     u=* (URI of description)
 *     e=* (zero or more email address with optional name of contacts)
 *     p=* (zero or more phone number with optional name of contacts)
 *     c=* (connection information—not required if included in all media)
 *     b=* (zero or more bandwidth information lines)
 *     One or more Time descriptions ("t=" and "r=" lines; see below)
 *     z=* (time zone adjustments)
 *     k=* (encryption key)
 *     a=* (zero or more session attribute lines)
 *     Zero or more Media descriptions (each one starting by an "m=" line; see below)
 *
 *
 *
 *      {"type":"answer","sdp":"v=0\r\no=- 8160090540521631164 2 IN IP4 127.0.0.1\r\ns=-\r\nt=0 0\r\na=group:BUNDLE 0\r\na=extmap-allow-mixed\r\na=msid-semantic: WMS\r\nm=application 9 UDP/DTLS/SCTP webrtc-datachannel\r\nc=IN IP4 0.0.0.0\r\na=candidate:3291593895 1 udp 2113937151 5aaf19a3-cadf-435e-aa86-c959e05d7525.local 54075 typ host generation 0 network-cost 999\r\na=ice-ufrag:szD1\r\na=ice-pwd:u1l5IWKagNyg+v+j83+0kt5z\r\na=ice-options:trickle\r\na=fingerprint:sha-256 5B:62:E7:D2:AB:B0:A3:AC:BD:A2:B2:EF:36:AC:B1:E3:AF:2F:AB:11:00:3B:8E:04:A2:14:13:9B:2E:29:0D:F5\r\na=setup:active\r\na=mid:0\r\na=sctp-port:5000\r\na=max-message-size:262144\r\n"}
 */

public class SessionDescription {


}
