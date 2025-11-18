package br.com.fiap.oasis.oasis.domain.ritual;

import jakarta.validation.constraints.*;


public record RitualRequestDto(
    
    @NotBlank(message = "{ritual.titulo.obrigatorio}")
    String titulo,

    @NotNull 
    @Min(value = 1, message = "A duração mínima deve ser de 1 minuto") 
    Integer duracaoMinutos,

    @NotBlank 
    String objetivo 
) {}