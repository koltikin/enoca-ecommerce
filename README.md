# Enoca Ecommerce Application

## Introduction
This project is a web-based ecommerce application offering role-based management functionality.

## Features
- **Product Management:** The root user can create, update, and delete products.
- **Customers:** Customers can add products to their cart, update their cart, place orders, and view their orders.
- **Stock Management:** Product stock is automatically reduced when a customer places an order.
- **Role-Based Access Control:** Customers can be assigned roles such as 'user' or 'root'. By default, when a customer account is created, the role is 'user'. If a 'realm-admin' user creates an account in Keycloak, they can assign roles like 'user' or 'root'.
- **Comprehensive Swagger OpenAPI Documentation:** The application includes Swagger documentation, accessible at [Swagger UI](https://enoca.yukseluyghur.com/swagger-ui/index.html).
- **Protected with Keycloak Authentication:** The application uses Keycloak as its authentication provider.

## Technology Stack
- Java 17
- Spring Boot v3.3.0
- RESTful API
- Spring Data, JPA & Hibernate
- PostgreSQL
- Maven
- Lombok
- Swagger-UI
- JSON
- Keycloak
- Nginx
- Docker

## Application Overview

### Branches
- **'main'** branch: Contains swagger-ui
- **'role-based-security-version'** branch: Contains Keycloak Authentication and Role based Authorization implementation
- **'deployement'** branch: include necessary deployment configurations

### Database ER Diagram
<img width="1119" alt="image" src="https://github.com/koltikin/enoca-ecommerce/assets/56764495/4154a1cd-1b95-4bf0-9719-233b99768ae1">


