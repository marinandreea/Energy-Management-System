import React, { useEffect, useState,useRef  } from 'react';
import Table from '../Table';
import { useParams } from 'react-router-dom';
import GetErrorPageForUser from './GetErrorPageForUser';
import { useTable, userRowSelect } from 'react-table';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

function ManageUserPage() {
  const { id } = useParams();
  const [records, setRecords] = useState([]);
  const [flag, setFlag] = useState(false);
  const [websocketMessages, setWebsocketMessages] = useState([]);

  const columns = React.useMemo(
    () => [
      {
        Header: 'ID',
        accessor: 'id',
      },
      {
        Header: 'Description',
        accessor: 'description',
      },
      {
        Header: 'Address',
        accessor: 'address',
      },
      {
        Header: 'MaxHourlyEnergyConsumption',
        accessor: 'maxHourlyEnergyConsumption',
      },
    ],
    []
  );

  useEffect(() => {
    // Fetch devices data
    fetch(`http://localhost:8081/deviceByUser/${id}`, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('tokenAuthentication'),
      },
    })
      .then((response) => {
        if (response.status === 404) {
          setFlag(true);
        } else {
          return response.json();
        }
      })
      .then((data) => {
        setRecords(data);
      })
      .catch((err) => console.log(err));

    
  }, [id]);

  const stompClientRef = useRef(null);
  const subscriptionRef = useRef(null);

  useEffect(() => {
    // Set up WebSocket connection
    const socket = new SockJS('http://localhost:8082/ws');
    stompClientRef.current = Stomp.over(socket);
   

    const connectToWebSocket = () => {
      stompClientRef.current.connect(
        {},
        () => {
          console.log('Connected to WebSocket');

          
          const userId = id;

          // Check if there is an existing subscription
          if (subscriptionRef.current) {
            // Unsubscribe before creating a new one
            subscriptionRef.current.unsubscribe();
          }

          if (stompClientRef.current && stompClientRef.current.connected) {
            // Subscribe to the user-specific channel
            subscriptionRef.current = stompClientRef.current.subscribe(`/user/${userId}/userNotification`, (payload) => {
              // Handle incoming WebSocket message
              console.log('Raw message:', payload.body);
        
              // Try to parse the message as JSON
              let parsedMessage;
              try {
                parsedMessage = JSON.parse(payload.body);
        
                // Log the parsed message
                console.log('Parsed message:', parsedMessage);
        
                // Update the state with the new message
                setWebsocketMessages((prevMessages) => [...prevMessages, parsedMessage]);
              } catch (e) {
                console.error('Failed to parse message as JSON:', e);
              }
            });
          } else {
            console.error('WebSocket connection is not established.');
          }}
      );
    };

    connectToWebSocket();

    // Clean up the WebSocket connection on component unmount
    return () => {
      if (stompClientRef.current && stompClientRef.current.connected) {
        if (subscriptionRef.current) {
          try {
            subscriptionRef.current.unsubscribe();
          } catch (error) {
            console.error('Error unsubscribing:', error);
          }
        }
        stompClientRef.current.disconnect();
      }
    };

  }, [id]);




  return (
    <div>
      {flag ? (
        <GetErrorPageForUser />
      ) : (
        <>
         <div>
         <Table records={records} columns={columns} message="Devices Table" microservice="deviceD" flag={flag} />
          </div> 
          <div className='webskt'>
            <h2>WebSocket Messages:</h2>
            <ul>
              {websocketMessages.map((msg, index) => (
                <li key={index}>{msg.message}</li>
              ))}
            </ul>
          </div>
        </>
      )}
    </div>
  );
}

export default ManageUserPage;






