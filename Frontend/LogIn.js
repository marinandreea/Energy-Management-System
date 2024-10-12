import React from "react";

function LogIn(link){






    return(

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
                  {/* {this.state.role === 'ADMIN' && <Link to={'/fetch'}>
                    <button className='logBtn'>Login</button>
                    </Link>}
                    {this.state.role === 'CLIENT' && <Link to={'/manageDevicesPage'}>
                    <button className='logBtn'>Login</button>
                    </Link>}
                    <button className='logBtn'>Login</button>
                    {this.state.status === 403 && <h1>Incorrect username or password! </h1>}
                    <button className='logBtn'>Login</button> */}
                    {/* {buttonOrMessage} */}
                    {/* <Link to={'/manageDevicesPage'}> */}
                   
                   <Link to={`/${link}`}>
                    <button className='logBtn' >Login</button>
                    </Link>
                   
                    {/* </Link> */}
                </div>
                </form>
            </div> 
            
              {/* <button>Login</button> */}
              {/* <a href='/manageUserPage'>Admin</a> */} 
          </div>
        </div>
      </div> 
    </div>

    )
}