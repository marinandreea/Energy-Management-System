package com.example.DS_Assignment1_B2_Devices.security;

import com.example.DS_Assignment1_B2_Devices.UserDeserializer;
import com.example.DS_Assignment1_B2_Devices.model.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@JsonDeserialize(using = UserDeserializer.class)
public class CustomUser {

    private String username;
    private String password;
    private String role;

    public CustomUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public CustomUser(){}

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

    public String getRoles() {
        return role;
    }

    public void setRoles(String role) {
        this.role = role;
    }
}
