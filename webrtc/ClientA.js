const WebRTCConnection = new RTCPeerConnection({
  iceServers: [
    {
      urls: 'stun:stun1.l.google.com:19302',
    },
  ],
});

const chatChannel = WebRTCConnection.createDataChannel('chat');
chatChannel.onmessage = (event) => console.log('onmessage:', event.data);
chatChannel.onopen = () => console.log('onopen');
chatChannel.onclose = () => console.log('onclose');

WebRTCConnection.onicecandidate = (event) => {
  if (event.candidate)
    console.log('localDescription:', JSON.stringify(WebRTCConnection.localDescription));
};

WebRTCConnection.createOffer().then((localDescription) => {
  WebRTCConnection.setLocalDescription(localDescription);
});

const remoteDescription = /* Add localDescription from client B here */;
WebRTCConnection.setRemoteDescription(remoteDescription);