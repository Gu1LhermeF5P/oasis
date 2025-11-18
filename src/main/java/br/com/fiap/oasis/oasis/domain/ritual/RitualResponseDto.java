package br.com.fiap.oasis.oasis.domain.ritual;

public record RitualResponseDto(
    Long id,
    String titulo,
    String sugestaoIA,
    Integer duracao
) {
    
    public RitualResponseDto(Ritual ritual) {
        this(ritual.getId(), ritual.getTitulo(), ritual.getDescricaoGeradaPorIA(), ritual.getDuracaoMinutos());
    }
}