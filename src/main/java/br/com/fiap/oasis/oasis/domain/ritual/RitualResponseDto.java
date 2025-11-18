package br.com.fiap.oasis.domain.ritual;

public record RitualResponseDto(
    Long id,
    String titulo,
    String sugestaoIA,
    Integer duracao
) {
    // Construtor auxiliar que converte Entity -> DTO
    public RitualResponseDto(Ritual ritual) {
        this(ritual.getId(), ritual.getTitulo(), ritual.getDescricaoGeradaPorIA(), ritual.getDuracaoMinutos());
    }
}