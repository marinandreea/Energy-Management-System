import axios from "axios";
import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Link } from 'react-router-dom';

function UpdateUser() {
  const [name, setName] = useState('');
  const [role, setRole] = useState('');
  const navigate = useNavigate();
  const { id } = useParams();

  const handleChange = (e) => {
    if (e.target.name === 'name') {
      setName(e.target.value);
    } else if (e.target.name === 'role') {
      setRole(e.target.value);
    }
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!name || !role) {
      alert("Name and Role fields are empty so the record is not modified");
      navigate('/fetch'); // Navigate back to the fetch page
      return;
    }
    if(role != 'ADMIN' && role != 'CLIENT'){
      alert("Role can only be ADMIN or CLIENT");
      navigate(`/update/${id}`); 
      return;
    }

    axios.put(`http://localhost:8080/user/update/${id}`, { name, role },
    {headers: { 
      'Content-Type' : 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
  }})
      .then(response => {
        console.log(response);
        navigate('/fetch');
      })
      .catch((err) => console.log(err));
  }



  return (
    <div className="updatePg">
      
           

<h1 className="hh">Update User</h1>
      <div className="updateUser">
        <form onSubmit={handleSubmit}>
          <div className="usrN">
            <input type="text" placeholder='Name' name='name' className='nameUsr' value={name} onChange={handleChange}></input>
          </div>
          <div className="usrR">
            <input type="text" placeholder='Role' name='role' className='rolee' value={role} onChange={handleChange}></input>
          </div>
          <div className="updBtn">
            <button className="updt">Update</button>
          </div>
        </form>
      </div>
             
      
    </div>
  );
}

export default UpdateUser;