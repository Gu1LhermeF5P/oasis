package br.com.fiap.oasis.oasis.config;

import br.com.fiap.oasis.oasis.domain.ritual.Ritual;
import br.com.fiap.oasis.oasis.repository.RitualRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Profile("!test") // Não roda nos testes unitários
public class DataSeeder {

    @Bean
    public CommandLineRunner loadData(RitualRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                System.out.println("🌱 Seed: Populando banco de dados com rituais iniciais...");
                
                Ritual r1 = Ritual.builder()
                        .titulo("Respiração Box")
                        .duracaoMinutos(5)
                        .descricaoGeradaPorIA("Inspire por 4s, segure por 4s, expire por 4s, segure por 4s. Repita para acalmar o sistema nervoso.")
                        .dataCriacao(LocalDateTime.now().minusDays(1))
                        .build();

                Ritual r2 = Ritual.builder()
                        .titulo("Pausa Pomodoro")
                        .duracaoMinutos(25)
                        .descricaoGeradaPorIA("Foque intensamente por 25 minutos. Ao final, levante-se e alongue as costas.")
                        .dataCriacao(LocalDateTime.now())
                        .build();

                repository.saveAll(List.of(r1, r2));
            }
        };
    }
}
