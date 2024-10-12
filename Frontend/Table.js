
import { useTable, userRowSelect } from 'react-table';
import {useState,useEffect} from 'react'
import React from 'react';
import { CheckIcon, DeviceTabletIcon, PencilIcon, TrashIcon, XIcon } from '@heroicons/react/outline';
import axios from 'axios';
import { Link } from 'react-router-dom';
import PostFieldsUser from './user/PostFieldsUser';
import {Line} from 'react-chartjs-2'
import {Chart as ChartJS, LineElement, CategoryScale, LinearScale, PointElement, Legend, Tooltip} from 'chart.js'
import './Table.css'

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

ChartJS.register(
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
  Legend,
  Tooltip
)

      
function Table({records, columns, PostFields, message, microservice, flag, status}){

    const data = React.useMemo(() => records, [records]);
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [chartData, setChartData] = useState([]);

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
        selectedFlatRows
      } = useTable({ columns, data }, userRowSelect);

      const handleOnClickDelete = (id) =>{
        if(microservice === 'user'){
            axios.delete(`http://localhost:8080/user/delete/${id}`,
            {headers: { 
              'Content-Type' : 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
          }})
        .then(() => {
            alert('data has been deleted')
            //onDataChange();
        })
        .catch(error => {
            // Handle other errors that might occur
            console.error('Error:', error);
            console.log(error.response.data)
            
          });
        }else{
            axios.delete(`http://localhost:8081/device/delete/${id}`,
            {headers: { 
              'Content-Type' : 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
          }})
        .then(() => {
            alert('data has been deleted')
            //onDataChange();
        })
        .catch(error => {
            // Handle other errors that might occur
            console.error('Error:', error);
            console.log(error.response.data)
            
          });
        }
        
      }

      
      // useEffect(() => {
      //   const fetchData = async () => {
      //     try {
      //        const response = await fetch(`http://localhost:8082/chart/`
      //       //, {
      //       //   method: 'GET',
      //       //   headers: {
      //       //     'Content-Type': 'application/json',
      //       //     'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
      //       //   },}
      //       );
    
      //       if (!response.ok) {
      //         throw new Error("No authorization");
      //       }
    
      //       const data = await response.json();
      //       console.log(data);
    
      //       setChartData(data);
      //     } catch (error) {
      //       console.error('Error:', error);
      //     }
      //   };
    
      //   fetchData();
      // }, []);

      // const handleDateChange = (date) => {
      //   setSelectedDate(date);
      // };

      const handleDateChange = async (date,id) => {
        setSelectedDate(date);
    
        try {
          // const response = await fetch(`http://localhost:8082/chart/${id}`, {
          //   method: 'GET',
          //   headers: {
          //     'Content-Type': 'application/json',
          //     'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
          //   },
          // });
          const response = await fetch(`http://localhost:8082/chart/${id}`, {
            headers: {
              'Content-Type': 'application/json',
              Authorization: 'Bearer ' + localStorage.getItem('tokenAuthentication'),
            },});
    
          if (!response.ok) {
            throw new Error("Error fetching chart data");
          }
    
          const data = await response.json();
          setChartData(data);
        } catch (error) {
          console.error('Error:', error);
        }
      };

      const dataa = {
        labels: chartData.map(entry => {
          // Assuming entry.timestamp is an array like [21, 32, 14, 918000000]
          const [hours, minutes, seconds] = entry.timestamp.slice(0, 3);
          return `${hours}:${minutes}:${seconds}`;
        }),
    datasets:[{
      label:'Historical energy consumption', 
      data: chartData.map(entry => entry.measurement_value),
      backgroundColor:'aqua',
      borderColor:'black',
      pointBorderColor:'aqua',
      tension:0.4
    }]}
    const options = {
      plugins:{
        legend:true
      },
      scales:{
         y:{
          //min:3, max:6
        }
      }
    }
      
    
      

    
  



    return (
        <div className="fetchData">
          {status === 403 ? (<div>
            <Link to={'/'}>
            <button className='logOutButton'>Logout</button>
            </Link>
            <h1>No authorization to access this resource! you are not an admin!</h1>
          </div>
            ):(
            <div>

{microservice === 'user' ? (<div className='navButtons'>
            <Link to={'/manageDevicesPage'}>
            <button className='devButton'>Devices</button>
            </Link>
            <Link to={'/'}>
            <button className='logOutButton'>Logout</button>
            </Link>
           
          </div>):(
            <div>
              {microservice === 'deviceD' ?  (
              <div className='navButtons'>
              <Link to={'/'}>
            <button className='logOutButton'>Logout</button>
            </Link></div>):(
              <div className='navButtons'>
              <Link to={'/fetch'}>
              <button className='devButton'>Users</button>
              </Link>
              <Link to={'/'}>
              <button className='logOutButton'>Logout</button>
              </Link>
             
            </div>


            )}
            </div>
            

          )}
          <h2 className="title">{message}</h2>
          {flag  && <p>No devices are associated to this user</p>}
          {records.length === 0 ? (
            <p>No records found.</p>
          ) : (
            <div className="container">
              <table {...getTableProps()}>
                <thead>
                  {headerGroups.map((headerGroup) => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                      {headerGroup.headers.map((column) => (
                        <th {...column.getHeaderProps()}>{column.render("Header")}</th>
                      ))}
                     {PostFields && microservice === 'user' ? (<th>Edit/Delete/Get Dev</th>) : ( PostFields && microservice === 'device'  && <th>Edit/Delete</th>)}
                     {microservice === 'deviceD' && <th>History consumption</th>}
                    </tr>
                  ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                  {rows.map((row) => {
                    prepareRow(row);
                    return (
                      <tr {...row.getRowProps()}>
                        {row.cells.map((cell) => (
                          <td {...cell.getCellProps()}>{cell.render("Cell")}</td>
                        ))}
                        {PostFields &&
                        <td>
                            {microservice === 'user' ? (
                                <Link to={`/update/${row.original.id}`}>
                                <button className="editButton" id="editBtn">
                                <PencilIcon className="w-10 h-10"></PencilIcon>
                                </button>
                            </Link>
                            ):(
                                <Link to={`/updateD/${row.original.id}`}>
                                <button className="editButton" id="editBtn">
                                <PencilIcon className="w-10 h-10"></PencilIcon>
                                </button>
                            </Link>
                            )

                            }
                          <button className="deleteButton" id="deleteBtn" onClick={() => handleOnClickDelete(row.original.id)}>
                            <TrashIcon className="w-10 h-10" />
                          </button>
                          { microservice === 'user' && (
                            <Link to={`/getDevicesUser/${row.original.id}`}>
                              <button className='getDevicesButton' id="getDevBtn">
                                <DeviceTabletIcon className="w-10 h-10"></DeviceTabletIcon>
                              </button>
                            </Link>
                            
                          )}

                        </td>}
                        {microservice === 'deviceD' && 
                        <td>

                          <div className='chaart'>
                          <DatePicker selected={selectedDate} onChange={(date) => handleDateChange(date,row.original.id)} />
                          <Line data={dataa} options={{options}}></Line>
                          </div>


                        </td>}
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>)}
            {PostFields &&  <PostFields></PostFields>}



            </div>
          )}
          
           
        </div> 
        
  );    
}


export default Table;