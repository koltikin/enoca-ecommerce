package com.enoca;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnocaEcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnocaEcommerceApplication.class, args);
    }

    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }

}
