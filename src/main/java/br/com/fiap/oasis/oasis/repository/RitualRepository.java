package br.com.fiap.oasis.oasis.repository;

import br.com.fiap.oasis.oasis.domain.ritual.Ritual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RitualRepository extends JpaRepository<Ritual, Long> {
    // Método customizado para suportar paginação (Requisito da entrega)
    Page<Ritual> findAll(Pageable pageable);
}