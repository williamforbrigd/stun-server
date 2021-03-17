const remoteDescription = /* Add a localDescription from client A here */;

const WebRTCConnection = new RTCPeerConnection({
  iceServers: [
    {
      urls: 'stun:stun1.l.google.com:19302',
    },
  ],
});

let chatChannel;
WebRTCConnection.ondatachannel = (event) => {
  if (event.channel.label == 'chat') {
    chatChannel = event.channel;
    chatChannel.onmessage = (event) => console.log('onmessage:', event.data);
    chatChannel.onopen = () => console.log('onopen');
    chatChannel.onclose = () => console.log('onclose');
  }
};

WebRTCConnection.onicecandidate = (event) => {
  if (event.candidate)
    console.log('localDescription:', JSON.stringify(WebRTCConnection.localDescription));
};

WebRTCConnection.setRemoteDescription(remoteDescription);

WebRTCConnection.createAnswer().then((localDescription) => {
  WebRTCConnection.setLocalDescription(localDescription);
});