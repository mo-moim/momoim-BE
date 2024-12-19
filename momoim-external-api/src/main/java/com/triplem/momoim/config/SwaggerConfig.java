package com.triplem.momoim.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Momoim API Docs", version = "V1"),
        servers = {@Server(url = "/", description = "Default Server Url")}
)
public class SwaggerConfig {

    private static final String SECURITY_SCHEME = "bearer";
    private static final String BEARER_FORMAT = "JWT";
    private static final String SECURITY_SCHEME_NAME = "Authorization";
    private static final String SECURITY_SCHEME_ITEM_NAME = "bearerAuth";

    @Bean
    public OpenAPI getOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme(SECURITY_SCHEME)
                .bearerFormat(BEARER_FORMAT)
                .in(SecurityScheme.In.HEADER)
                .name(SECURITY_SCHEME_NAME);
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_ITEM_NAME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_ITEM_NAME, securityScheme));
    }
}
