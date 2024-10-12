import axios from "axios";
import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

function UpdateDevices() {
  const [description, setDescription] = useState('');
  const [address, setAddress] = useState('');
  const [maxHourlyEnergyConsumption, setMaxHourlyEnergyConsumption] = useState('');
  const navigate = useNavigate();
  const { id } = useParams();

  const handleChange = (e) => {
    if (e.target.name === 'description') {
      setDescription(e.target.value);
    } else if (e.target.name === 'address') {
      setAddress(e.target.value);
    }else if(e.target.name === 'maxHourlyEnergyConsumption'){
        setMaxHourlyEnergyConsumption(e.target.value);
    }
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!description || !address || !maxHourlyEnergyConsumption) {
      alert("Some fields are empty so the record is not modified");
      navigate('/manageDevicesPage'); 
      return;
    }
    if(maxHourlyEnergyConsumption <= 0){
      alert("Energy Consumption can't be 0 or less than 0! Try again!");
      navigate(`/updateD/${id}`); 
      return;
    }

    axios.put(`http://localhost:8081/device/update/${id}`, { description, address, maxHourlyEnergyConsumption },
    {headers: { 
      'Content-Type' : 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
  }})
      .then(response => {
        console.log(response);
        navigate('/manageDevicesPage');
      })
  }

  return (
    <div className="updatePg">
      <h1 className="hh">Update Device</h1>
      <div className="updateDevice">
        <form onSubmit={handleSubmit}>
          <div className="devD">
            <input type="text" placeholder='Description'name="description" className='des' value={description} onChange={handleChange}></input>
          </div>
          <div className="devA">
            <input type="text" placeholder='Address' name="address" className='add' value={address} onChange={handleChange}></input>
          </div>
          <div className="devE">
            <input type="numeric" placeholder='EnergyConsumption' name="maxHourlyEnergyConsumption" className='maxHourly' value={maxHourlyEnergyConsumption} onChange={handleChange}></input>
          </div>
          <div className="updBtn">
            <button className="updt">Update</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default UpdateDevices;