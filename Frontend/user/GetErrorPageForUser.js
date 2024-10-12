import React from "react";
import { Link } from 'react-router-dom';

class GetErrorPageForUser extends React.Component{

    render(){

        return(
            <div>
                
                <Link to={'/'}>
                    <button className='logOutButton'>Logout</button>
                </Link>
                <h1>No device was associated to this user</h1>
            </div>
        )

    }
       
    
}

export default GetErrorPageForUser;