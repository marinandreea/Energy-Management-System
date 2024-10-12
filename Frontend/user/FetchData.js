import React, { useState, useEffect } from 'react';
import { useTable } from 'react-table';
import './FetchData.css';
import { CheckIcon, PencilIcon, TrashIcon, XIcon } from '@heroicons/react/outline';
import Table from '../Table';
import PostFieldsDevice from '../devices/PostFieldsDevice';
import PostFieldsUser from './PostFieldsUser';



function  FetchData() {
  
    
    const [records, setRecords] = useState([]);
    const[status, setStatus] = useState('');
    const columns = React.useMemo(
    () => [
      {
        Header: "Id",
        accessor: "id",
      },
      {
        Header: "Name",
        accessor: "name",
      },
      {
        Header: "Username",
        accessor: "username",
      },
      {
        Header: "Password",
        accessor: "password",
      },
      {
        Header: "Role",
        accessor: "role",
      },
    ],
    []
  );

 

  useEffect(() => { 
    const resp =  fetch('http://localhost:8080/user', {
      method:'GET',
      headers: {
              'Content-Type' : 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
              
            },
    })
    .then((response) => {
      if (!response.ok) {
        setStatus(response.status);
        console.log(response.status);
        throw new Error("No authorization");
       
      }
      return response.json();
    })
      .then((data) => {
        setRecords(data);
        //console.log(data)
      })
      .catch((err) => console.log(err));
  }, []);


 


  const data = React.useMemo(() => records, [records]);
  console.log(data);
  console.log(localStorage.getItem("tokenAuthentication"));
  
    return (
    
    <Table records={records} columns={columns} PostFields = {PostFieldsUser} message="User Accounts Table" microservice="user" flag={false} status={status} />
   
  
    )
  
  
}

export default FetchData;