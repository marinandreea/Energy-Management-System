package com.example.DS_Assignment1_B2_Devices.controller;

import com.example.DS_Assignment1_B2_Devices.controller.errorHandler.Error;
import com.example.DS_Assignment1_B2_Devices.dtos.DeviceDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.UserDTO;
import com.example.DS_Assignment1_B2_Devices.model.Device;
import com.example.DS_Assignment1_B2_Devices.model.Userr;
import com.example.DS_Assignment1_B2_Devices.service.DeviceService;
import com.example.DS_Assignment1_B2_Devices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@EnableMethodSecurity
public class UserController {

   @Autowired
    private UserService userService;
   @Autowired
   private DeviceService deviceService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST,value = "/notify/{id}")
    public ResponseEntity<?> addUser(@PathVariable int id){
        UserDTO userDTO = new UserDTO(id);
        Optional<Userr> user2 = userService.addUser(userDTO);
        if(user2.isEmpty()){
            Error error = new Error("User could not be added!");
            return new ResponseEntity<>(error,HttpStatus.NO_CONTENT);
        }
            return new ResponseEntity<>(user2,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,value = "/notifyDelete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        Optional<List<Device>> devicesDeleted = deviceService.deleteDevicesByUserId(id);
        Optional<Userr> user2 = userService.deleteUser(id);
        if(user2.isEmpty()){
            Error error = new Error("User could not be deleted!");
            return new ResponseEntity<>(error,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user2,HttpStatus.OK);
    }
}
