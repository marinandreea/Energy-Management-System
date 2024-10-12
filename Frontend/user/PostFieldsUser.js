import React, {Component} from "react";
import axios from "axios";



class PostFieldsUser extends Component{

    constructor(props){
        super(props)

        this.state ={
            name: '',
            username: '',
            password: '',
            role:'',
            errorFields:'',
            errorUsername:'',
            status:''
        }
    }

    handleChange = (e) =>{
        this.setState({
            [e.target.name]: e.target.value
        })
    }

    handleSubmit = (e)=>{
        e.preventDefault()

        if (this.state.name === '' || this.state.username === '' || this.state.password === '' || this.state.role === '') {
            this.setState({
                errorFields: 'Please fill in all fields', // Set error message
            });
            return; // Do not make the POST request if fields are empty
        }
        if(this.state.role !== 'ADMIN' && this.state.role !== 'CLIENT'){
            this.setState({
                errorFields: 'Role can be only ADMIN or CLIENT!', 
            });
            return;
        }

        this.setState({ errorFields: ''});

        axios.post('http://localhost:8080/user/create', this.state, 
        {headers: { 
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
        }})
        .then(response =>{
            this.setState({errorUsername: ''})
            alert('User was added successfully!')
            
        })
        .catch(err =>{
            if(err.response){
                this.setState({ errorUsername: err.response.data });
            console.error(err);
        } else {
            // Handle network errors or other unexpected errors
            console.error('Error:', err);
        }
            
        })
    }

    render(){


    const{name, username, password, role, errorFields,  errorUsername} = this.state


     return(
           
     <div className="inputs">
        <form onSubmit={this.handleSubmit}>
            <div className="inputss">
                <input type="text" placeholder='Name' name='name' className='nameUser' value={name} onChange={this.handleChange}></input>
                <input type="text" placeholder='Username' name='username' className='username' value={username} onChange={this.handleChange}></input>
                <input type="password" placeholder='Password'  name='password' className='pass' value={password} onChange={this.handleChange}></input>
                <input type="text" placeholder='Role' name='role'  className='role' value={role} onChange={this.handleChange}></input>
            </div>
            {errorFields && <p className="error-message">{errorFields}</p>}
            {errorUsername && <p className="error">This username already exists! Try with a different one!</p>}
            <div>
              <button type='submit' className='addUserBtn'>Add user</button>
             
            </div>
         </form>
    </div>
        )
    }
}



export default PostFieldsUser;