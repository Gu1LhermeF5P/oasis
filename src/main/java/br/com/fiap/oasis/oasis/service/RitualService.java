package br.com.fiap.oasis.oasis.service;

import br.com.fiap.oasis.oasis.domain.ritual.*;
import br.com.fiap.oasis.oasis.repository.RitualRepository;
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

@Service
public class RitualService {

    @Autowired
    private RitualRepository repository;

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional
    @CacheEvict(value = "rituais", allEntries = true)
    public Ritual gerarRitualComIA(RitualRequestDto dto) {
        
        String prompt = "Crie uma dica rápida e prática de saúde mental para: " + dto.objetivo();
        String sugestao;

        // --- BLINDAGEM CONTRA FALHAS DA OPENAI ---
        try {
            sugestao = chatModel.call(prompt);
        } catch (Exception e) {
            // Se a API falhar (sem saldo, erro de rede), usamos um texto padrão para não quebrar a tela
            System.err.println("ERRO NA IA: " + e.getMessage());
            sugestao = "Sugestão offline: Respire fundo por 5 minutos e beba um copo d'água. (Serviço de IA indisponível no momento).";
        }

        Ritual ritual = Ritual.builder()
                .titulo(dto.titulo())
                .duracaoMinutos(dto.duracaoMinutos())
                .descricaoGeradaPorIA(sugestao)
                .dataCriacao(LocalDateTime.now())
                .build();
        
        repository.save(ritual);

        
        try {
            rabbitTemplate.convertAndSend("oasis-exchange", "ritual.created", ritual.getId());
        } catch (Exception e) {
            System.err.println("Falha ao enviar mensagem para RabbitMQ: " + e.getMessage());
        }
        
        return ritual;
    }

    @Cacheable(value = "rituais")
    public Page<Ritual> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    
    @Transactional
    @CacheEvict(value = "rituais", allEntries = true)
    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
    public Ritual buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ritual não encontrado: " + id));
    }

    
    @Transactional
    @CacheEvict(value = "rituais", allEntries = true)
    public void atualizar(Long id, RitualRequestDto dto) {
        Ritual ritual = buscarPorId(id);
        
        // Atualiza os dados
        ritual.setTitulo(dto.titulo());
        ritual.setDuracaoMinutos(dto.duracaoMinutos());
        
        repository.save(ritual);
    }
}