package com.desafio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hackaton2 - Simulador de Crédito API")
                        .version("1.0")
                        .description("API para simulação de crédito, consulta de produtos, telemetria e relatórios.")
                        .contact(new Contact().name("Equipe Hackaton").email("hackaton@caixa.gov.br"))
                        .license(new License().name("MIT"))
                );
    }
}
