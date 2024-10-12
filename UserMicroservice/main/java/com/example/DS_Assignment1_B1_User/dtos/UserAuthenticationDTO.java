package com.example.DS_Assignment1_B1_User.dtos;

import com.example.DS_Assignment1_B1_User.model.Role;

public class UserAuthenticationDTO {

    private String username;
    private String password;
    private String role;

    public UserAuthenticationDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserAuthenticationDTO(){}

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
