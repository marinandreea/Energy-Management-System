package com.example.DS_Assignment1_B1_User.dtos;

import com.example.DS_Assignment1_B1_User.model.Role;


public class UserDetailsDTO {


    private int ID;

    private String name;

    private String username;

    private String password;

    private Role role;

    public UserDetailsDTO(int ID, String name, String username, String password, Role role) {
        this.ID = ID;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public UserDetailsDTO(){}

    public UserDetailsDTO(String name, String username, String password, Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }




}
