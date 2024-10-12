package com.example.DS_Assignment1_B2_Devices.service;

import com.example.DS_Assignment1_B2_Devices.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

    @Autowired
    private RestTemplate restTemplate; // You need to configure and inject RestTemplate.

    private final String userMicroserviceUrl = "http://localhost:8080/userByUsername/{username}";
    private final String userMicroserviceUrlD = "http://user-microservice:8080/userByUsername/{username}";


    public CustomUser loadUserByUsernameAndToken(String username, String authToken) throws UsernameNotFoundException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        String token = "Bearer " + authToken; // Replace with your actual authentication token
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CustomUser> userResponse = restTemplate.exchange(userMicroserviceUrl, HttpMethod.GET, entity, CustomUser.class, username);

        HttpStatus responseStatus = (HttpStatus) userResponse.getStatusCode();
        System.out.println("Response Status: " + responseStatus);

        if (responseStatus == HttpStatus.OK) {
            CustomUser user = userResponse.getBody();

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            CustomUser customUser = new CustomUser(
                    user.getUsername(),
                    user.getPassword(),
                     user.getRoles()
            );
            System.out.println(customUser.getUsername() + " " + customUser.getPassword()+ " " + customUser.getRoles());


            return customUser;
        } else {
            // Handle the case when the User Microservice returns a 404 status (Not Found).
            // Log the response body to understand the error message.
            String responseBody = userResponse.getBody() != null ? userResponse.getBody().toString() : "No response body";
            System.out.println("Response Body: " + responseBody);

            // Throw a UsernameNotFoundException or perform other error handling here.
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
}