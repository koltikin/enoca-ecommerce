package com.enoca.service.impl;

import com.enoca.config.KeycloakProperties;
import com.enoca.dto.KeycloakUser;
import com.enoca.exception.EnocaEcommerceProjectException;
import com.enoca.service.CustomerService;
import com.enoca.service.KeycloakService;
import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final KeycloakProperties keycloakProperties;

    @Value("${keycloak.realm_name}")
    private String realm;


    @Override
    public List<KeycloakUser> getUserFromKeycloak() {
        Keycloak keycloak = keycloakProperties.getKeycloakInstance();
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().list();
        return mapToCustomers(userRepresentations);
    }

    @Override
    @Transactional
    public KeycloakUser createKeycloakUser(KeycloakUser keycloakUser) {

        if (!keycloakUser.getRole().equals("user") && !keycloakUser.getRole().equals("root") ){
            throw new EnocaEcommerceProjectException("Role should be 'user', or root");
        }
        UserRepresentation userRep = mapToUserRep(keycloakUser);
        Keycloak keycloak = keycloakProperties.getKeycloakInstance();
        UsersResource usersResource = keycloak.realm(realm).users();
        // Create user
        Response response = usersResource.create(userRep);
        if (response.getStatus() != 201) {
            throw new EnocaEcommerceProjectException("Failed to create user in the keycloak");
        }

        // Retrieve user ID from response location
        String userId = getCreatedId(response);

        // Assign role to the user
        assignRealmRoleToUser(userId, keycloakUser.getRole(), keycloak);

        // Assign client-level role to the user
        assignClientRoleToUser(userId, keycloakProperties.getApplication_clientId(), keycloakUser.getRole(), keycloak);

        return keycloakUser;
    }

    private void assignRealmRoleToUser(String userId, String roleName, Keycloak keycloak) {
        UsersResource usersResource = keycloak.realm(realm).users();
        RoleRepresentation role = keycloak.realm(realm).roles().get(roleName).toRepresentation();
        usersResource.get(userId).roles().realmLevel().add(List.of(role));
    }

    private void assignClientRoleToUser(String userId, String clientId, String roleName, Keycloak keycloak) {
        UsersResource usersResource = keycloak.realm(realm).users();
        String clientUuid = keycloak.realm(realm).clients().findByClientId(clientId).get(0).getId();
        RoleRepresentation clientRole = keycloak.realm(realm).clients().get(clientUuid).roles().get(roleName).toRepresentation();
        usersResource.get(userId).roles().clientLevel(clientUuid).add(List.of(clientRole));
    }

    private UserRepresentation mapToUserRep(KeycloakUser keycloakUser) {
        UserRepresentation userRep = new UserRepresentation();

        userRep.setUsername(keycloakUser.getEmail());
        userRep.setFirstName(keycloakUser.getFirstName());
        userRep.setLastName(keycloakUser.getLastName());
        userRep.setEmail(keycloakUser.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);

        List<CredentialRepresentation> credentials = new ArrayList<>();
        CredentialRepresentation credentialRep = new CredentialRepresentation();
        credentialRep.setTemporary(false);
        credentialRep.setValue(keycloakUser.getPassWord());
        credentials.add(credentialRep);

        userRep.setCredentials(credentials);
        return userRep;
    }

    private List<KeycloakUser> mapToCustomers(List<UserRepresentation> userRepresentations) {
        List<KeycloakUser> customers = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userRepresentations)){
            userRepresentations.forEach(userRep->{
                customers.add(mapToCustomer(userRep));
            });
        }
        return customers;
    }

    private KeycloakUser mapToCustomer(UserRepresentation userRep) {
        KeycloakUser customer = new KeycloakUser();
        customer.setFirstName(userRep.getFirstName());
        customer.setLastName(userRep.getLastName());
        customer.setEmail(userRep.getEmail());
        return customer;
    }
}
