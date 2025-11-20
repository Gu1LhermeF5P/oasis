package br.com.fiap.oasis.oasis.config;

import br.com.fiap.oasis.oasis.repository.RitualRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class DataSeeder {

    @Bean
    public CommandLineRunner loadData(RitualRepository repository) {
        return args -> {
            
            System.out.println(" Oasis iniciado com sucesso! Aguardando criação de rituais...");
        };
    }
}