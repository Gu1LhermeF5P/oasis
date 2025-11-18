package br.com.fiap.oasis.oasis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // Quando acessar a raiz, joga para a aplicação
        return "redirect:/app/rituais"; 
    }
}