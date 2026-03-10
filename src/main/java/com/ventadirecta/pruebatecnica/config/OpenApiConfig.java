package com.ventadirecta.pruebatecnica.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Nombre del esquema de seguridad (puede ser cualquier string)
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Direct Sales API - Technical Test")
                        .version("1.0.0")
                        .description("REST API for product, store, and sales management")
                        .contact(new Contact()
                                .name("Direct Sales")
                                .email("example@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                // Agregar el requisito de seguridad global
                // Esto hace que TODOS los endpoints en Swagger muestren el candado 🔒
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // Definir CÓMO es el esquema de seguridad
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)  // Tipo HTTP
                                        .scheme("bearer")                // Esquema Bearer
                                        .bearerFormat("JWT")             // Formato JWT
                                        .description("Ingresa tu token JWT. Ejemplo: eyJhbGci...")
                        ));
    }
}