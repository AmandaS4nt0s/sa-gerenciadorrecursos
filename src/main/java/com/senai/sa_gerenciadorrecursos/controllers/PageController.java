package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.dtos.RecursoDto;
import com.senai.sa_gerenciadorrecursos.services.ColaboradorService;
import com.senai.sa_gerenciadorrecursos.services.RecursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    private final ColaboradorService colaboradorService;
    private final RecursoService recursoService;

    public PageController(ColaboradorService colaboradorService, RecursoService recursoService, RecursoService recursoService1) {
        this.colaboradorService = colaboradorService;
        this.recursoService = recursoService1;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    //-------------------------- COLABORADOR --------------------------
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
    //-------------------------- RECURSO --------------------------
    @GetMapping("/recursocadastrar")
    public String cadastrarRecurso(Model model){
        model.addAttribute("recurso", new RecursoDto());
        return "recursocadastrar";
    }
    @GetMapping("/recursolista")
    public String listarRecurso(Model model){
        model.addAttribute("recurso", recursoService.listarRecursos());
        return "recursolista";
    }
    @GetMapping("/recursoatualizar/{id}")
    public String atualizarRecurso(@PathVariable Long id, Model model) {
        model.addAttribute("recurso", recursoService.buscarPorId(id));
        return "recursoatualizar";
    }
}