import React from 'react'
import { useState,useEffect, useRef } from 'react'
import { over } from 'stompjs';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import './ChatRoom.css'
import { useParams } from 'react-router-dom';


const ChatRoomUser = () =>{

    const [publicChats, setPubliChats] = useState([]);
    const [privateChats, setPrivateChats] = useState(new Map());
    const {usernm} = useParams();
    const {adminName} = useParams();
    const [tab, setTab] = useState(adminName);
    
    console.log(usernm);
    const stompClient = useRef(null);
    const [isSubscribed, setIsSubscribed] = useState(false);

    const [isTyping, setIsTyping] = useState(false);
  

    const [userData, setUserData] = useState({
        username:usernm,
        receiverName:"",
        connected:false,
        message:""
    });
 

    useEffect(() => {
        if (!stompClient.current) {
            let socket = new SockJS("http://localhost:8082/ws");
            stompClient.current = Stomp.over(socket);
            let connectOptions = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
                },
            };
            stompClient.current.connect(connectOptions, onConnected, onError);
        }
    
        return () => {
            if (stompClient.current && stompClient.current.connected) {
                stompClient.current.disconnect();
                stompClient.current = null;
            }
        }
    }, []);

    const handleValue = (event)=>{
        const {value,name} = event.target;
        setUserData({...userData,[name]:value});
    }

    const registerUser = ()=>{
        let Sock = new SockJS("http://localhost:8082/ws");

        let connectOptions = {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
            },
        };
        console.log(connectOptions);
        stompClient = over(Sock);
        stompClient.connect(connectOptions,onConnected, onError);

    }

    const onConnected = () => {
        if (!isSubscribed) {
            setIsSubscribed(true);
            stompClient.current.subscribe('/adminChat/admin', onPublicMessageReceived);
            stompClient.current.subscribe(`/userChat/` + userData.username + `/private`, onPrivateMessageReceived);
            stompClient.current.subscribe(`/userChat/ `+ userData.username + `/private/typing`, onTypingNotification);
            userJoin();
        }
        setUserData({ ...userData, "connected": true });
        setTimeout(() => {
            if (!stompClient.current.connected) {
                console.log('Connection not established');
            }
        }, 3000); // increase delay to 3 seconds
    }
    const userJoin=()=>{
     
            let chatMessage={
                senderName:userData.username,
                status:'JOIN'
            };
            let connectOptions = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
                },
            };
            stompClient.current.send('/app/message',connectOptions,JSON.stringify(chatMessage));
    }


    const onError = (err) => {
        console.log('Failed to connect: ', err);
    }

    const onPublicMessageReceived=(payLoad)=>{
        let payloadData=JSON.parse(payLoad.body);
        switch(payloadData.status){
            case "JOIN":
                if(!privateChats.get(payloadData.senderName)){
                    privateChats.set(payloadData.senderName,[]);
                    setPrivateChats(new Map(privateChats));

                }
                break;
            case "MESSAGE":
                publicChats.push(payloadData);
                setPubliChats([...publicChats]);
                break;
        }
    }

    // const onPrivateMessageReceived = (payload)=>{
    //     console.log(payload);
    //     var payloadData = JSON.parse(payload.body);
    //     if(privateChats.get(payloadData.senderName)){
    //         privateChats.get(payloadData.senderName).push(payloadData);
    //         setPrivateChats(new Map(privateChats));
    //     }else{
    //         let list =[];
    //         list.push(payloadData);
    //         privateChats.set(payloadData.senderName,list);
    //         setPrivateChats(new Map(privateChats));
    //     }
    // }

    const onPrivateMessageReceived = (payload)=>{
        console.log(payload);
        var payloadData = JSON.parse(payload.body);
        if(payloadData.status === 'MESSAGE'){
            setPrivateChats((prevPrivateChats) => {
                const updatedPrivateChats = new Map(prevPrivateChats);
                const chatArray = updatedPrivateChats.get(payloadData.senderName) || [];
                chatArray.push({...payloadData, isRead: false});
                chatArray.push(payloadData);
                updatedPrivateChats.set(payloadData.senderName, chatArray);
                console.log(updatedPrivateChats);
    
                const lastItemKey = [...updatedPrivateChats.keys()].pop();
            const lastItemValue = updatedPrivateChats.get(lastItemKey);
            const updatedLastItemValue = lastItemValue ? lastItemValue.slice(0, -1) : [];
            console.log('Values array of the last item:', updatedLastItemValue);
            updatedPrivateChats.set(lastItemKey, updatedLastItemValue);
    
    
           
                return updatedPrivateChats;

        });
        
        setIsTyping(false);
        }else if(payloadData.status === 'TYPING'){
            setIsTyping(true);
        }else if (payloadData.status === 'READ') {
            setPrivateChats((prevPrivateChats) => {
              const updatedPrivateChats = new Map(prevPrivateChats);
              const chatArray = updatedPrivateChats.get(payloadData.senderName) || [];
              const messageIndex = chatArray.findIndex(message => message.id === payloadData.id);  // Find the message by id
              if (messageIndex !== -1) {
                chatArray[messageIndex].isRead = true;  // Mark the message as read
              }
              updatedPrivateChats.set(payloadData.senderName, chatArray);
              return updatedPrivateChats;
            });
          }
        else{
            setIsTyping(false);
        }  
    }

    const handleTyping = (event) => {
        const { value } = event.target;
    
        // Update local state
        setUserData({ ...userData, message: value });
    
        // Send typing notification to the server
        if (value.trim() !== "" && !isTyping) {
            let connectOptions = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
                },
            };
          // User started typing
          stompClient.current.send('/app/typing', connectOptions, JSON.stringify({ senderName: userData.username, receiverName: localStorage.getItem("adminUsername"), status: 'TYPING' }));
         
        } else if (value.trim() === "" && isTyping) {
            let connectOptions = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
                },
            };
          // User stopped typing
          stompClient.current.send('/app/typing', connectOptions, JSON.stringify({ senderName: userData.username, receiverName: localStorage.getItem("adminUsername"), status: 'STOP_TYPING' }));
         
        }
      };
      const onTypingNotification = (payload) => {
        const payloadData = JSON.parse(payload.body);
        if (payloadData.senderName !== userData.username) {
          setIsTyping(payloadData.status === 'TYPING');
          console.log(payloadData.status);
        }
      };

      
    const handleMessage = (event)=>{
        const {value} = event.target;
        setUserData({...userData,"message":value});
    }

    const sendPublicMessage=()=>{
        if(stompClient){
            let chatMessage={
                senderName:userData.username,
                message:userData.message,
                status:'MESSAGE'
            };
            let connectOptions = {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
                },
            };
            stompClient.current.send('/app/message',connectOptions,JSON.stringify(chatMessage));
            setUserData({...userData,"message":""});
        }

    }

    const sendPrivateMessage=()=>{
        if(stompClient){
            let chatMessage={
                senderName:userData.username,
                receiverName:tab,
                message:userData.message,
                status:'MESSAGE'
            };
            if (userData.username !== tab) {
                let chatArray = privateChats.get(tab) || [];
                chatArray.push(chatMessage);
                privateChats.set(tab, chatArray);
                setPrivateChats(new Map(privateChats));
            }
            let connectOptions = {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
                    'Content-Type': 'application/json'
                },
            };
            stompClient.current.send('/app/privateMessage',connectOptions,JSON.stringify(chatMessage));
            setUserData({...userData,"message":""});
            setIsTyping(false);
        }

    }
    
      
    

   



    return (
        <div className='containerChatRoom'>
          {userData.connected && (
            <div className='chat-box'>
              <div className='member-list'>
                <ul>
                  <li onClick={() => { setTab(localStorage.getItem("adminUsername")) }} className={`member ${tab === localStorage.getItem("adminUsername") && "active"}`}>{localStorage.getItem("adminUsername")}</li>
                </ul>
              </div>
              {tab === localStorage.getItem("adminUsername") && (
                <div className='chat-content'>
                  <ul className='chat-messages'>
                  {(isTyping) && <li className='message'>
                                    {<div className='avatar'>{tab}</div>}
                                    <div className='message-data'>is typing...</div>
                                </li>}
                    {privateChats.get(tab) && [...privateChats.get(tab)].map((chat, index) => (
                       
                      <li className='message' key={index}>
                        {chat.senderName !== userData.username && <div className='avatar'>{chat.senderName}</div>}
                        <div className='message-data'>{chat.message}</div>
                        {chat.isRead && <div className='read-label'>Read</div>}
                        {chat.senderName === userData.username && <div className='avatar self'>{chat.senderName}</div>}
                      </li>
                    ))}
                  </ul>
                  <div className='send-message'>
                    <input
                      type='text'
                      name='message'
                      className='input-message'
                      placeholder='enter private message'
                      value={userData.message}
                      onChange={handleTyping}
                    
                    />
                    <button type='button' className='send-button' onClick={sendPrivateMessage}>send</button>
                    
                  </div>
                  
                </div>
              )}
            </div>
          )}
        </div>
      );
    
}

export default ChatRoomUser