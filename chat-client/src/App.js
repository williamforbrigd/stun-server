
function App() {
  return (
    <div>
      <Chat1 />
      <Chat2 />
    </div>
  );
}

function Chat1() {
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

  //const remoteDescription = /* Add localDescription from client B here */;
  //WebRTCConnection.setRemoteDescription(remoteDescription);

  return(
    <div>
      Hello from Client A
    </div>
  );
}

function Chat2() {
  //const remoteDescription = /* Add a localDescription from client A here */;

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

  //WebRTCConnection.setRemoteDescription(remoteDescription);

  WebRTCConnection.createAnswer().then((localDescription) => {
    WebRTCConnection.setLocalDescription(localDescription);
  });

  return (
    <div>
      Hello from Client B
    </div>
  );

}


export default App;