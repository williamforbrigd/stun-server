import React, { useState } from 'react';

import firebase from 'firebase/app';
import 'firebase/firestore'; //for the database
import 'firebase/auth'; //for user authentication

import { useAuthState } from 'react-firebase-hooks/auth';
import { useCollectionData } from 'react-firebase-hooks/firestore';

const auth = firebase.auth();
const firestore = firestore.firestore();

firebase.initializeApp({

});

function App() {
  const [message, setMessage] = useState();

  const postRequest = () => {
    const requestOptions = {
      method: "POST",
      headers: { "Content-Type": "applicaton/json"},
      body: JSON.stringify(message)
    };
    fetch("http://localhost:8080/client", requestOptions)
      .then((response) => response.json())
      .then((result) => {
        setMessage(result.message)
      });
  }
  return(
    <div>
      <header>Chat</header>
    </div>
  );
}

function Chat() {
  const messagesRef = firestore.collection('messages');
  const query = messagesRes.orderBy('createdAt').limit(25);
  const [messages] = useCollectionData(query, {idField: 'id'})
}

export default App;