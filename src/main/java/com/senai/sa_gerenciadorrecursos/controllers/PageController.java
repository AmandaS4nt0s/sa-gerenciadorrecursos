package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.services.ColaboradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    private final ColaboradorService colaboradorService;

    public PageController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/colaboradorcadastrar")
    public String colaboradorCadastrar(Model model) {
        model.addAttribute("colaborador", new ColaboradorDto());
        return "colaboradorcadastrar";
    }

    @GetMapping("/colaboradorlista")
    public String colaboradorLista(Model model) {
        model.addAttribute("colaboradores", colaboradorService.listarColaboradores());
        return "colaboradorlista";
    }

    @GetMapping("/colaboradoratualizar/{id}")
    public String atualizarColaborador(@PathVariable Long id, Model model) {
        ColaboradorDto colaborador = colaboradorService.buscarPorId(id);
        model.addAttribute("colaborador", colaborador);
        return "colaboradoratualizar";
    }
}