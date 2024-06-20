package com.enoca.service.impl;

import com.enoca.config.KeycloakProperties;
import com.enoca.dto.CustomerDto;
import com.enoca.exception.EnocaEcommerceProjectException;
import com.enoca.service.KeycloakService;
import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;
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
    public List<CustomerDto> getUserFromKeycloak() {
        Keycloak keycloak = keycloakProperties.getKeycloakInstance();
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().list();
        return mapToCustomers(userRepresentations);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        UserRepresentation userRep = mapToUserRep(customer);
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
        assignRealmRoleToUser(userId, customer.getRole(), keycloak);

        // Assign client-level role to the user
        assignClientRoleToUser(userId, keycloakProperties.getApplication_clientId(), customer.getRole(), keycloak);


        return customer;
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

    private UserRepresentation mapToUserRep(CustomerDto customer) {
        UserRepresentation userRep = new UserRepresentation();

        userRep.setUsername(customer.getFirstName());
        userRep.setFirstName(customer.getFirstName());
        userRep.setLastName(customer.getLastName());
        userRep.setEmail(customer.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);

        List<CredentialRepresentation> credentials = new ArrayList<>();
        CredentialRepresentation credentialRep = new CredentialRepresentation();
        credentialRep.setTemporary(false);
        credentialRep.setValue(customer.getPassWord());
        credentials.add(credentialRep);

        userRep.setCredentials(credentials);
        return userRep;
    }

    private List<CustomerDto> mapToCustomers(List<UserRepresentation> userRepresentations) {
        List<CustomerDto> customers = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userRepresentations)){
            userRepresentations.forEach(userRep->{
                customers.add(mapToCustomer(userRep));
            });
        }
        return customers;
    }

    private CustomerDto mapToCustomer(UserRepresentation userRep) {
        CustomerDto customer = new CustomerDto();
        customer.setFirstName(userRep.getFirstName());
        customer.setLastName(userRep.getLastName());
        customer.setEmail(userRep.getEmail());
        return customer;
    }
}
