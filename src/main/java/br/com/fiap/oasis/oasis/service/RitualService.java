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

    // --- C: CREATE (Com IA, Lógica de Tempo e Mensageria) ---
    @Transactional
    @CacheEvict(value = "rituais", allEntries = true)
    public Ritual gerarRitualComIA(RitualRequestDto dto) {
        
        // 1. Prompt Otimizado: Pede algo curto e direto
        String prompt = "Crie uma dica de saúde mental MUITO CURTA e direta (máximo 200 caracteres) para: " + dto.objetivo();
        String sugestao;

        // 2. Blindagem contra falhas da IA (Groq/OpenAI)
        try {
            sugestao = chatModel.call(prompt);
        } catch (Exception e) {
            System.err.println("ERRO NA IA: " + e.getMessage());
            sugestao = "Sugestão offline: Respire fundo por 5 minutos e beba um copo d'água. (Serviço de IA indisponível no momento).";
        }

        // 3. Lógica de Tempo Inteligente (Se vier nulo/zero)
        Integer tempoFinal = dto.duracaoMinutos();
        if (tempoFinal == null || tempoFinal == 0) {
            String obj = dto.objetivo().toLowerCase();
            if (obj.contains("cansado") || obj.contains("exausto") || obj.contains("estresse")) {
                tempoFinal = 5; // Rituais rápidos para alívio
            } else if (obj.contains("foco") || obj.contains("trabalho") || obj.contains("estudar")) {
                tempoFinal = 25; // Pomodoro
            } else {
                tempoFinal = 10; // Padrão seguro
            }
        }

        // 4. Montagem do Objeto
        Ritual ritual = Ritual.builder()
                .titulo(dto.titulo())
                .duracaoMinutos(tempoFinal)
                .descricaoGeradaPorIA(sugestao)
                .dataCriacao(LocalDateTime.now())
                .build();
        
        // 5. Persistência
        repository.save(ritual);

        // 6. Mensageria (Com proteção contra queda do RabbitMQ)
        try {
            rabbitTemplate.convertAndSend("oasis-exchange", "ritual.created", ritual.getId());
        } catch (Exception e) {
            System.err.println("Falha ao enviar mensagem para RabbitMQ: " + e.getMessage());
        }
        
        return ritual;
    }

    // --- R: READ (Listar com Paginação e Cache) ---
    @Cacheable(value = "rituais")
    public Page<Ritual> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // --- U: UPDATE (Atualizar Dados) ---
    @Transactional
    @CacheEvict(value = "rituais", allEntries = true)
    public void atualizar(Long id, RitualRequestDto dto) {
        Ritual ritual = buscarPorId(id);
        
        // Atualiza dados manuais
        ritual.setTitulo(dto.titulo());
        
        // Se vier tempo na edição, atualiza. Se não, mantém o antigo.
        if (dto.duracaoMinutos() != null && dto.duracaoMinutos() > 0) {
            ritual.setDuracaoMinutos(dto.duracaoMinutos());
        }
        
        repository.save(ritual);
    }

    // --- D: DELETE (Remover) ---
    @Transactional
    @CacheEvict(value = "rituais", allEntries = true)
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Ritual não encontrado para exclusão");
        }
        repository.deleteById(id);
    }
    
    public Ritual buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ritual não encontrado: " + id));
    }
}