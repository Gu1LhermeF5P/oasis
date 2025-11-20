package br.com.fiap.oasis.oasis.domain.ritual;

import jakarta.validation.constraints.*;

public record RitualRequestDto(
    
    @NotBlank(message = "{ritual.titulo.obrigatorio}")
    String titulo,

    // REMOVIDO O @NotNull: Agora aceita vazio (para a IA decidir)
    @Min(value = 1, message = "A duração mínima é 1 minuto")
    Integer duracaoMinutos,

    @NotBlank 
    String objetivo 
) {}