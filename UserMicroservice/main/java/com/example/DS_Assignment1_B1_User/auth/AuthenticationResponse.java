package com.example.DS_Assignment1_B1_User.auth;

import com.example.DS_Assignment1_B1_User.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private int ID;
    private Role role;
}
