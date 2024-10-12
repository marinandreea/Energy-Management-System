package com.example.DS_Assignment1_B1_User.dtos.builders;

import com.example.DS_Assignment1_B1_User.dtos.LogInDTO;
import com.example.DS_Assignment1_B1_User.dtos.LogInDetailsDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserAuthenticationDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserDetailsDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserUpdateDTO;
import com.example.DS_Assignment1_B1_User.model.Userr;

public class UserBuilder {

    public UserBuilder() {
    }

    public static UserDetailsDTO toUserDetailsDTO(Userr user){
        return new UserDetailsDTO(user.getID(), user.getName(), user.getUsername(), user.getPassword(), user.getRole());
    }
    public static UserDetailsDTO toUserDTO(Userr user){
        return new UserDetailsDTO( user.getName(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public static UserUpdateDTO toUserUpdateDTO(Userr user){
        return new UserUpdateDTO(user.getID(), user.getName(), user.getRole());
    }

    public static LogInDTO toUserLogInDTO(Userr user){
        return new LogInDTO(user.getID(), user.getRole());
    }
    public static LogInDetailsDTO toUserLogInDetailsDTO(Userr user){
        return new LogInDetailsDTO(user.getUsername(), user.getPassword());
    }

    public static UserAuthenticationDTO toUserAuthenticationDTO(Userr user){
        return new UserAuthenticationDTO(user.getUsername(), user.getPassword(), String.valueOf(user.getRole()));
    }


    public static Userr toEntity(UserDetailsDTO userDetailsDTO){
        return new Userr(userDetailsDTO.getID(),
                userDetailsDTO.getName(),
                userDetailsDTO.getUsername(),
                userDetailsDTO.getPassword(),
                userDetailsDTO.getRole());
    }
    public static Userr toEntity(UserDTO userDTO){
        return new Userr(
                userDTO.getName(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole());
    }
}
