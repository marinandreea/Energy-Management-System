package com.example.DS_Assignment1_B2_Devices.dtos.builders;

import com.example.DS_Assignment1_B2_Devices.dtos.UserDTO;
import com.example.DS_Assignment1_B2_Devices.model.Userr;

public class UserBuilder {

    public UserBuilder(){}

    public static UserDTO toUserDTO(Userr user){
        return new UserDTO(user.getID());
    }

    public static Userr toEntity(UserDTO userDTO){
        System.out.println(userDTO.getID());
        return new Userr(userDTO.getID());
    }



}
