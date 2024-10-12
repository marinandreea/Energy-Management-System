package com.example.DS_Assignment1_B2_Devices;

import com.example.DS_Assignment1_B2_Devices.security.CustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper2() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CustomUser.class, new UserDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
