package com.example.DS_Assignment1_B2_Devices.service;

import com.example.DS_Assignment1_B2_Devices.dtos.DeviceDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.UserDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.builders.DeviceBuilder;
import com.example.DS_Assignment1_B2_Devices.dtos.builders.UserBuilder;
import com.example.DS_Assignment1_B2_Devices.model.Device;
import com.example.DS_Assignment1_B2_Devices.model.Userr;
import com.example.DS_Assignment1_B2_Devices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Userr> addUser(UserDTO userDTO){
        Userr user = UserBuilder.toEntity(userDTO);
        System.out.println(user.getID());
        return Optional.of(userRepository.save(user));
    }

    public Optional<Userr> deleteUser(int id){
        Optional<Userr> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return Optional.of(user.get());
        }
        return Optional.empty();
    }
}
