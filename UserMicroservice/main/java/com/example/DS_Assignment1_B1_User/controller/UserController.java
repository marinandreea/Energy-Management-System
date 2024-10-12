package com.example.DS_Assignment1_B1_User.controller;

import com.example.DS_Assignment1_B1_User.controller.errorHandler.Error;
import com.example.DS_Assignment1_B1_User.dtos.LogInDTO;
import com.example.DS_Assignment1_B1_User.dtos.LogInDetailsDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserAuthenticationDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserDetailsDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserUpdateDTO;
import com.example.DS_Assignment1_B1_User.model.Userr;
import com.example.DS_Assignment1_B1_User.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController

@EnableMethodSecurity
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDetailsDTO>getAllUsers(){
        List<UserDetailsDTO> users = userService.getAllUsers();
        return users;
    }


    @RequestMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id){
        Optional<UserDetailsDTO> user = userService.getUserById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        Error error = new Error("Could not find user with id " + id);
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.POST,value = "/user/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody UserDTO user,@RequestHeader("Authorization") String authorizationHeader){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Optional<Userr> userToAdd = userService.addUser(user);

        if (userToAdd.isEmpty()) {
            Error error = new Error("Could not add user because this username is already taken");
            return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }

        // Notify device service
        String deviceServiceUrl = "http://localhost:8081/notify/{id}";
        String deviceServiceUrlD = "http://devices-microservice:8081/notify/{id}";
        ResponseEntity<Void> deviceServiceResponse = restTemplate.exchange(deviceServiceUrl, HttpMethod.POST, entity, Void.class,userToAdd.get().getID());

        if (deviceServiceResponse.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(userToAdd, HttpStatus.OK);
        }

        userService.deleteUser(userToAdd.get().getID());
        Error error = new Error("Could not add user!");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.PUT,value = "/user/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO user, @PathVariable int id){
        Optional<Userr> userOptional = userService.updateUser(id, user);
        if(userOptional.isEmpty()){
            Error error = new Error("User could not be updated because the id could not be found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userOptional.get(),HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.DELETE,value="/user/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable int id, @RequestHeader("Authorization") String authorizationHeader){

        Optional<UserDetailsDTO> user = userService.getUserById(id);

        if(!user.isEmpty()){

            String deviceServiceUrl = "http://localhost:8081/notifyDelete/{id}";
            String deviceServiceUrlD = "http://devices-microservice:8081/notifyDelete/{id}";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);

            HttpEntity<String> entity = new HttpEntity<>(headers);


            ResponseEntity<Void> deviceServiceResponse = restTemplate.exchange(deviceServiceUrl, HttpMethod.DELETE,  entity, Void.class, id);

            if (deviceServiceResponse.getStatusCode().is2xxSuccessful()) {

                Optional<Userr> userToDelete = userService.deleteUser(id);

                if (userToDelete.isPresent()) {

                    return new ResponseEntity<>(userToDelete, HttpStatus.OK);
                } else {

                    Error error = new Error("Could not delete user");
                    return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
                }
            } else {

                Error error = new Error("Could not notify device service");
                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        Error error = new Error("Could not find user");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @RequestMapping("/userByUsername/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        Optional<UserAuthenticationDTO> user = userService.getUserByUsername(username);
        if(!user.isEmpty()){
            System.out.println(user.get().getUsername() + user.get().getPassword() + user.get().getRole());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        Error error = new Error("User could not be found!");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
