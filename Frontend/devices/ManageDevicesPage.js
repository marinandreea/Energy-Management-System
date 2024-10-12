import React from 'react'
import '../devices/ManageDevicesPage.css';
import { useState, useEffect } from 'react';
import { useTable } from 'react-table';
import { PencilIcon, TrashIcon, CheckIcon, XIcon } from '@heroicons/react/outline';
import Table from '../Table';
import PostFieldsDevice from './PostFieldsDevice';

function ManageDevicesPage(){

  const [records, setRecords] = useState([]);
  const[status, setStatus] = useState('');
  const columns = React.useMemo(
    () => [
      {
        Header: "Id",
        accessor: "id",
      },
      {
        Header: "Description",
        accessor: "description",
      },
      {
        Header: "Address",
        accessor: "address",
      },
      {
        Header: "MaxHourlyEnergyConsumption",
        accessor: "maxHourlyEnergyConsumption",
      },
      {
        Header: "User id",
        accessor: (row) => row.user_id?.id,
      },
    ],
    []
  );

  useEffect(() => {
    fetch('http://localhost:8081/device',
    {headers: { 
      'Content-Type' : 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
  }})
      .then((response) => {

        if (!response.ok) {
          setStatus(response.status);
          console.log(response.status);
          throw new Error("No authorization");
         
        }
       return response.json();
      }
      )
      .then((data) => {
        setRecords(data);
      })
      .catch((err) => console.log(err));
  }, []);

  const data = React.useMemo(() => records, [records]);
  console.log(data);

  return (
    //Table(records, columns, PostFieldsDevice, 'Devices Table','device', false)
    <Table records={records} columns={columns} PostFields = {PostFieldsDevice} message="Devices Table" microservice="device" flag={false} status={status} />
  );
}

export default ManageDevicesPage