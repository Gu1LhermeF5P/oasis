package br.com.fiap.oasis.oasis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Oasis API - Equilíbrio Híbrido")
                        .description("API para gestão de bem-estar corporativo utilizando IA Generativa.")
                        .contact(new Contact().name("Time Oasis").email("contato@oasis.fiap.com.br"))
                        .version("1.0.0"));
    }
}