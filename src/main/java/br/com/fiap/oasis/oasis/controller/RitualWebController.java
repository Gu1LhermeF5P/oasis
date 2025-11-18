package br.com.fiap.oasis.oasis.controller;

import br.com.fiap.oasis.oasis.domain.ritual.RitualRequestDto;
import br.com.fiap.oasis.oasis.service.RitualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/rituais")
public class RitualWebController {

    @Autowired
    private RitualService service;

    @GetMapping
    public String home(Model model) {
        var lista = service.listar(Pageable.unpaged());
        model.addAttribute("rituais", lista.getContent());
        return "home";
    }

    @PostMapping("/novo")
    public String criarRitual(RitualRequestDto dto) {
        service.gerarRitualComIA(dto);
        return "redirect:/app/rituais";
    }

    
    @PostMapping("/deletar/{id}")
    public String deletarRitual(@PathVariable Long id) {
        service.deletar(id); // Você precisará criar este método no Service
        return "redirect:/app/rituais";
    }
}