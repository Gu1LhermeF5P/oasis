package br.com.fiap.oasis.oasis.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RitualConsumer {

    @RabbitListener(queues = "oasis.rituais.novos") 
    public void onRitualCreated(Long ritualId) {
        
        System.out.println("===============================================");
        System.out.println(" NOTIFICAÇÃO: Novo ritual criado! ID: " + ritualId);
        System.out.println("===============================================");
    }
}
