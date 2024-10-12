import './App.css';
import profile from './images/a.png';
import email from './images/email.jpg';
import passw from './images/pass.png';
import React from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useHistory } from 'react-router-dom';


class App extends React.Component {
  
  constructor(props){
    super(props)

    this.state ={
        username: '',
        password: '',
        status: '',
        role: '',
        error: null,
        ID:'',
    }
}

handleChange = (e) =>{
    this.setState({
        [e.target.name]: e.target.value
    })
}

handleSubmit = (e)=>{
  e.preventDefault()
  axios.post('http://localhost:8080/api/v1/auth/authenti', this.state)
  .then(response =>{
    this.setState({status: response.status})
    this.setState({error: null})
    console.log(response.status)
    if(response.status === 200){
      localStorage.setItem("tokenAuthentication", response.data.token)
      this.setState({ role: response.data.role, ID: response.data.id})
      if(response.data.role === 'ADMIN'){
        localStorage.setItem("adminUsername", this.state.username);
      }
      
      
    }
      console.log(response)
      alert('User has correct credentials!')
      
  })
  .catch(err =>{
    this.setState({error: 'error'})
      console.error(err)

  })

  
}


 
  render() {

  const{username, password, status, role, linkTo, error, ID} = this.state

  return (
    <div className='main'>
      <div className='sub-main'>
        <div className='body-main'>
          <div className='imgs'>
            <div className='container-image'>
              <img src={profile} alt="profile" className='profile'>
              </img>
            </div>
          </div>

          <div className='body'>
            <h1>Please login!</h1>
            <div className='input-container'>
              <form onSubmit={this.handleSubmit}>
                <div className='input'>
                  <img src={email} alt="email" className='email'></img>
                  <input type="text" placeholder='username' name="username" value={username} className='name' onChange={this.handleChange}></input>
                </div>
                <div className='second-input'>
                  <img src={passw} alt="password" className='password'></img>
                  <input type="password" placeholder='password' name='password' value={password}  className='name' onChange={this.handleChange}></input>
                </div>
                
                <div>
                  
                <button className='logBtn'>Check credentials</button>
           

                  {this.state.status === 200 ? (
                      <div>
                        <h1>Correct credentials!</h1>
                        {this.state.role === 'ADMIN' ? (
                          <div>
                            <Link to={'/fetch'}>
                         <button className='uBtn'>User</button>
                         </Link>
                         <Link to={'/manageDevicesPage'}>
                         <button className='dBtn'>Devices</button>
                         </Link>
                         <Link to={`/chat/${this.state.username}`}>
                         <button className='cBtn'>Chat</button>
                         </Link>
                          </div>):(
                            <div>
                              <Link to={`/getDevicesUser/${this.state.ID}`}>
                         <button className='devBtn'>Devices</button>
                         </Link>
                         <Link to={`/chatUser/${this.state.username}`}>
                         <button className='chBtn'>Chat</button>
                         </Link>
                            </div>
                          
                          )
                         }</div>
                    
                     
                        ):(
                          <div>
                            {this.state.error && <h1>Incorrect username or password. Please try again!</h1>}
                          </div>
                           
                        )} 
                </div>
                </form>
            </div>  
          </div>
        </div>
      </div> 
    </div>
  )

 };

  
}

export default App;
