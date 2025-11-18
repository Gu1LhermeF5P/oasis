package br.com.fiap.oasis.oasis.controller;

import br.com.fiap.oasis.oasis.domain.ritual.RitualRequestDto;
import br.com.fiap.oasis.oasis.service.RitualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Atenção: Não é RestController
@RequestMapping("/app/rituais")
public class RitualWebController {

    @Autowired
    private RitualService service;

    @GetMapping
    public String home(Model model) {
        // Busca os rituais no banco usando o Service que já criamos
        var lista = service.listar(Pageable.unpaged());
        
        // Adiciona a lista no modelo para o Thymeleaf usar (${rituais})
        model.addAttribute("rituais", lista.getContent());
        
        return "home"; // Retorna o arquivo home.html
    }

    @PostMapping("/novo")
    public String criarRitual(RitualRequestDto dto) {
        // Chama a IA e salva
        service.gerarRitualComIA(dto);
        
        // Redireciona para a home para ver o novo card
        return "redirect:/app/rituais";
    }
}