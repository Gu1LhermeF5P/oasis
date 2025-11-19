package br.com.fiap.oasis.oasis.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    
    @Bean
    public Queue queue() {
        // Cria a fila automaticamente se não existir
        return new Queue("oasis.rituais.novos", true);
    }
}