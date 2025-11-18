package br.com.fiap.oasis.oasis.controller;

import br.com.fiap.oasis.oasis.domain.ritual.*;
import br.com.fiap.oasis.oasis.service.RitualService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rituais")
public class RitualController {

    @Autowired private RitualService service;

    @PostMapping
    @Operation(summary = "Criar Ritual via IA", description = "Gera conteúdo personalizado usando GenAI")
    public ResponseEntity<Ritual> criar(@RequestBody @Valid RitualRequestDto dto) {
        // Retorna status 201 (Created) com o objeto criado
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gerarRitualComIA(dto));
    }

    @GetMapping
    @Operation(summary = "Listar Rituais", description = "Retorna lista paginada de rituais")
    public ResponseEntity<Page<Ritual>> listar(
        @PageableDefault(size = 5) Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(pageable));
    }
}