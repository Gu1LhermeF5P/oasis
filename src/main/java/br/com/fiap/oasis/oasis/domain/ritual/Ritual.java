package br.com.fiap.oasis.oasis.domain.ritual;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_RITUAIS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ritual {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    
    @Lob // Permite salvar textos longos (ex: sugestões grandes da IA)
    private String descricaoGeradaPorIA;
    
    private Integer duracaoMinutos;
    
    private LocalDateTime dataCriacao;
}