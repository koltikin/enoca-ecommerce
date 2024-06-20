package com.enoca.controller;


import com.enoca.dto.KeycloakUser;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.KeycloakService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keycloak")
@SecurityRequirement(name = "keycloak")
@Tag(name = "Keycloak", description = "Operations related to keycloak")
public class KeycloakUserController {
    private final KeycloakService keycloakService;

    @Operation(summary = "Get all the customers from keycloak enoca-dev realm, only root user can do this)",
            description = "This endpoint allows you to get all the customers in the enoca-dev realm from keycloak")
    @GetMapping(value = "/users")
    public ResponseEntity<ResponseWrapper> getCustomerList(){
        List<KeycloakUser> customerList = keycloakService.getUserFromKeycloak();
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Keycloak user are successfully retrieved")
                        .data(customerList)
                        .build()
        );
    }

    @Operation(summary = "Create customer to keycloak enoca-dev realm, only root user can do this)",
            description = "This endpoint allows you to create customer to keycloak enoca-dev realm. " +
                    "but the user will not be saved in application database")

    @PostMapping(value = "/user/create")
    public ResponseEntity<ResponseWrapper> createKeycloakCustomer(@Valid @RequestBody KeycloakUser keycloakUser){
        KeycloakUser savedKeycloakUser = keycloakService.createKeycloakUser(keycloakUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseWrapper.builder()
                                .success(true)
                                .code(HttpStatus.CREATED.value())
                                .message("customer is successfully created")
                                .data(savedKeycloakUser)
                                .build()
                );
    }

}
