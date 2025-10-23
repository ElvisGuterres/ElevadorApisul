package com.apisul.elevador.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor Local"),
                @Server(url = "https://elevadorapisul-production.up.railway.app", description = "Servidor Production")
        }
)
public class OpenApiConfig {
}
