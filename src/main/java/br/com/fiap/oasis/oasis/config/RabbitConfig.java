package br.com.fiap.oasis.oasis.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    
    // 1. Cria a Fila
    @Bean
    public Queue queue() {
        return new Queue("oasis.rituais.novos", true);
    }

    // 2. Cria a Exchange (que estava faltando e dando erro)
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("oasis-exchange");
    }

    // 3. Faz a ligação (Binding) entre a Exchange e a Fila
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        
        return BindingBuilder.bind(queue).to(exchange).with("ritual.created");
    }
}