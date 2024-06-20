package com.enoca.service;

import com.enoca.dto.KeycloakUser;

import java.util.List;

public interface KeycloakService {
    List<KeycloakUser> getUserFromKeycloak();

    KeycloakUser createKeycloakUser(KeycloakUser keycloakUser);
}
