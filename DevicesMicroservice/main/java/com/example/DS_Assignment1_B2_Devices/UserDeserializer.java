package com.example.DS_Assignment1_B2_Devices;

import com.example.DS_Assignment1_B2_Devices.model.Role;
import com.example.DS_Assignment1_B2_Devices.security.CustomUser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDeserializer extends JsonDeserializer<CustomUser> {
    @Override
    public CustomUser deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
        JsonNode rootNode = objectMapper.readTree(p);

        String username = rootNode.get("username").asText();
        String password = rootNode.get("password").asText();

        // Extract roles from the JSON as a list of strings
        JsonNode rolesNode = rootNode.get("role");
        String role = rootNode.get("role").asText();

//        if (rolesNode != null) {
//            for (JsonNode roleNode : rolesNode) {
//                roleList.add(roleNode.asText());
//            }
//        }

        // Create and return a CustomUser object
        CustomUser customUser = new CustomUser(username, password, role);
        return customUser;
    }
}