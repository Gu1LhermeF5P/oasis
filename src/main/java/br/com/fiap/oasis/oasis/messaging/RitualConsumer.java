package br.com.fiap.oasis.oasis.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RitualConsumer {

    @RabbitListener(queues = "oasis.rituais.novos") 
    public void onRitualCreated(Long ritualId) {
        log.info("📨 MENSAGERIA: Recebido evento de novo ritual ID: {}", ritualId);
        // Simulação de envio de e-mail ou push notification
    }
}