package br.com.fiap.oasis.oasis.service; 

import br.com.fiap.oasis.oasis.domain.ritual.*;
import br.com.fiap.oasis.oasis.repository.RitualRepository;
import lombok.extern.slf4j.Slf4j; // Import do Lombok
import org.springframework.ai.chat.model.ChatModel; 
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j // Adiciona a variável 'log' automaticamente
@Service
public class RitualService {

    @Autowired private RitualRepository repository;
    @Autowired private ChatModel chatModel; 
    @Autowired private RabbitTemplate rabbitTemplate;

    @Transactional
    @CacheEvict(value = "rituais", allEntries = true)
    public Ritual gerarRitualComIA(RitualRequestDto dto) {
        log.info("Iniciando geração de ritual para objetivo: {}", dto.objetivo());

        String prompt = "Crie uma dica rápida e prática de saúde mental para: " + dto.objetivo();
        String sugestao = chatModel.call(prompt);
        
        log.info("IA gerou sugestão com sucesso. Tamanho: {} caracteres", sugestao.length());

        Ritual ritual = Ritual.builder()
                .titulo(dto.titulo())
                .duracaoMinutos(dto.duracaoMinutos())
                .descricaoGeradaPorIA(sugestao)
                .dataCriacao(LocalDateTime.now())
                .build();
        
        repository.save(ritual);

        try {
            rabbitTemplate.convertAndSend("oasis-exchange", "ritual.created", ritual.getId());
            log.info("Evento de criação enviado para fila RabbitMQ.");
        } catch (Exception e) {
            log.error("Falha ao enviar mensagem para RabbitMQ: ", e);
        }
        
        return ritual;
    }

    @Cacheable(value = "rituais")
    public Page<Ritual> listar(Pageable pageable) {
        log.debug("Buscando rituais no banco (paginado)");
        return repository.findAll(pageable);
    }
}