package com.example.DS_Assignment1_B1_User.service;

import com.example.DS_Assignment1_B1_User.dtos.LogInDTO;
import com.example.DS_Assignment1_B1_User.dtos.LogInDetailsDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserAuthenticationDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserDetailsDTO;
import com.example.DS_Assignment1_B1_User.dtos.UserUpdateDTO;
import com.example.DS_Assignment1_B1_User.dtos.builders.UserBuilder;
import com.example.DS_Assignment1_B1_User.model.Userr;
import com.example.DS_Assignment1_B1_User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<UserDetailsDTO> getAllUsers(){
        List<Userr> users = new ArrayList<>();
        userRepository.findAll().forEach(u-> users.add(u));
        return users.stream().map(UserBuilder::toUserDetailsDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDetailsDTO> getUserById(int id){
        Optional<Userr> user = userRepository.findById(id);
        if(!user.isPresent()){
            return Optional.empty();
        }
        return Optional.of(UserBuilder.toUserDetailsDTO(user.get()));
    }

    public Optional<Userr> addUser(UserDTO userDTO){

        if(userDTO.getName() != null && userDTO.getUsername()!= null && userDTO.getPassword()!=null && userDTO.getRole()!=null){
            Optional<Userr> user = userRepository.findByUsername(userDTO.getUsername());
            if(!user.isPresent()){
                Userr userr = UserBuilder.toEntity(userDTO);
                String encP = userr.encryptedPassword(userr.getPassword());
                userr.setPassword(encP);
                return Optional.of(userRepository.save(userr));

            }
        }
        return Optional.empty();
    }


    public Optional<Userr> updateUser(int id, UserUpdateDTO userUpdateDTO){
        Optional<Userr> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setName(userUpdateDTO.getName());
            user.get().setRole(userUpdateDTO.getRole());
            return Optional.of(userRepository.save(user.get()));
        }
       return Optional.empty();
    }

    public Optional<Userr> deleteUser(int id){
        Optional<Userr> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return Optional.of(user.get());
        }
        return Optional.empty();
    }

    public Optional<UserAuthenticationDTO> getUserByUsername(String username){
        Optional<Userr> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return Optional.of(UserBuilder.toUserAuthenticationDTO(user.get()));
        }
        return Optional.empty();
    }

}
