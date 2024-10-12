package com.example.DS_Assignment1_B1_User.dtos;

import com.example.DS_Assignment1_B1_User.model.Role;

public class LogInDTO {

    private int ID;
    private Role role;

    public LogInDTO(int ID, Role role) {
        this.ID = ID;
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
