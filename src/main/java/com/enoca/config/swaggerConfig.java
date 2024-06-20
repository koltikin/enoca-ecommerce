package com.enoca.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "keycloak",
        openIdConnectUrl = "https://kc.yukseluyghur.com/realms/enoca-dev/.well-known/openid-configuration",
        scheme = "bearer",
        type = SecuritySchemeType.OPENIDCONNECT,
        in = SecuritySchemeIn.HEADER
)

public class swaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Enoca-ecommerce application OpenApi")
                        .version("v1")
                        .description("Enoca-ecommerce Application Open Api documentation"))
                .servers(List.of(new Server().url("http://localhost:8080")
                        .description("local server"), new Server().url("https://dev.cydeo.com")
                        .description("dev environment")));


    }

    private Info getInfo() {
        return new Info()
                .title("Enoca-ecommerce application Rest")
                .description("Api documentation")
                .version("v1.0");
    }

}
