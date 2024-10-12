import React, {Component} from "react";
import axios from "axios";



class PostFieldsDevice extends Component{

  constructor(props){
    super(props)

    this.state ={
        description: '',
        address: '',
        maxHourlyEnergyConsumption: '',
        user_id: '',
        error: '',
    }
  }

  handleChange = (e) =>{
    this.setState({
      [e.target.name]: e.target.name === 'user_id' || e.target.name === 'maxHourlyEnergyConsumption' ? parseInt(e.target.value, 10) : e.target.value
    })
  }

  handleSubmit = (e)=>{
    e.preventDefault()

    if (this.state.description === '' || this.state.address === '' || this.state.maxHourlyEnergyConsumption === '' || this.state.user_id === '') {
      this.setState({
          error: 'Please fill in all fields', 
      });
      return; // Do not make the POST request if fields are empty
  }
  if(this.state.maxHourlyEnergyConsumption === 0){
    this.setState({
      error: 'Energy Consumption field cannot be 0!',
  });
  return; 
  }
    console.log(this.state)
    axios.post('http://localhost:8081/device/create', this.state,
    {headers: { 
      'Content-Type' : 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem("tokenAuthentication"),
  }})
    .then(response =>{
       
      console.log(response)
   
        console.log(response)
        console.log(response.data)
        this.setState({ error: '' });
        alert("Device added successfully!");
        
    })
    .catch(err => {
      this.setState({ error: err.response.data.errorMessage });

    });
 }
   
  render(){

    const{description, address, maxHourlyEnergyConsumption, user_id, error, errorFields} = this.state

    return(

      
        <div className='inputs'>
        <form onSubmit={this.handleSubmit}>
        {this.state.error && <div className="error-message">{this.state.error}</div>}
          <div>
            <input type="text" placeholder='Description'name="description" className='description' value={description} onChange={this.handleChange}></input>
            <input type="text" placeholder='Address' name="address" className='address' value={address} onChange={this.handleChange}></input>
            <input type="text" placeholder='EnergyConsumption' name="maxHourlyEnergyConsumption" className='maxHourlyEnergyConsumption' value={maxHourlyEnergyConsumption} onChange={this.handleChange}></input>
            <input type="text" placeholder='User id' name="user_id" className='userId' value={user_id} onChange={this.handleChange}></input>
          </div>
          {errorFields && <p className="error-message">{errorFields}</p>}
          <div>
            <button type='submit' className='addDeviceBtn'>Add device</button>
          </div>
       </form>
     </div>

     
      
  )

  }
  
}

export default PostFieldsDevice;