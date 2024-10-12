package com.example.DS_Assignment1_B1_User.dtos;

import com.example.DS_Assignment1_B1_User.model.Role;

public class UserUpdateDTO {

    private int ID;
    private String name;
    private Role role;

    public UserUpdateDTO(int ID, String name, Role role) {
        this.ID = ID;
        this.name = name;
        this.role = role;
    }
    public UserUpdateDTO(){}

    public UserUpdateDTO(String name, Role role) {
        this.name = name;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
