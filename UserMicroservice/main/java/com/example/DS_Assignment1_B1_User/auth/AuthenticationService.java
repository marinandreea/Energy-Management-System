package com.example.DS_Assignment1_B1_User.auth;

import com.example.DS_Assignment1_B1_User.config.JwtService;
import com.example.DS_Assignment1_B1_User.model.Userr;
import com.example.DS_Assignment1_B1_User.repository.UserRepository;
import com.example.DS_Assignment1_B1_User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private  final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).get();
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).ID(user.getID()).role(user.getRole()).build();
    }
}
