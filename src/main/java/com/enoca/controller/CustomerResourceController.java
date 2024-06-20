package com.enoca.controller;

import com.enoca.config.KeycloakProperties;
import com.enoca.dto.CustomerDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.mapper.MapperUtil;
import com.enoca.service.KeycloakService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keycloak")
@SecurityRequirement(name = "keycloak")
@RequiredArgsConstructor
public class CustomerResourceController {
    private final KeycloakService keycloakService;

    @GetMapping(value = "/users")
    public ResponseEntity<ResponseWrapper> getCustomerList(){
        List<CustomerDto> customerList = keycloakService.getUserFromKeycloak();
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Keycloak user are successfully retrieved")
                        .data(customerList)
                        .build()
        );
    }


    @PostMapping(value = "/user/create")
    public ResponseEntity<ResponseWrapper> createKeycloakCustomer(@Valid @RequestBody CustomerDto customer){
        CustomerDto savedCustomer = keycloakService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseWrapper.builder()
                                .success(true)
                                .code(HttpStatus.CREATED.value())
                                .message("customer is successfully created")
                                .data(savedCustomer)
                                .build()
                );
    }

}
