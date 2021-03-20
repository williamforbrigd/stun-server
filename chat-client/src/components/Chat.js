import React, { useState } from 'react';
import { TextField } from '@material-ui/core';

function Chat() {
    const [users, setUsers] = useState([]);
    return(
        <div>
            <div className="title">
                Chat Application
            </div>
            <div className="bottom-style">
                <TextField />
            </div>
        </div>
    );
}

export default Chat;

