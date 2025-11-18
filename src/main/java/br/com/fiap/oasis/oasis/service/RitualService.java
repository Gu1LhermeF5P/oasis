@Transactional
    @CacheEvict(value = "rituais", allEntries = true)
    public Ritual gerarRitualComIA(RitualRequestDto dto) {
        
        String prompt = "Crie uma dica rápida e prática de saúde mental para: " + dto.objetivo();
        String sugestao;

        // BLINDAGEM CONTRA FALHAS DA OPENAI
        try {
            sugestao = chatModel.call(prompt);
        } catch (Exception e) {
            // Se der erro na API (créditos, rede, etc), usa um texto padrão
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
        
        // Mensageria (sem travar o fluxo)
        try {
            rabbitTemplate.convertAndSend("oasis-exchange", "ritual.created", ritual.getId());
        } catch (Exception e) {
            System.err.println("Falha ao enviar mensagem para RabbitMQ: " + e.getMessage());
        }
        
        return ritual;
    }