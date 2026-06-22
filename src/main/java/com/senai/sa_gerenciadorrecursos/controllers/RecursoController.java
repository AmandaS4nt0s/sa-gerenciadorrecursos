package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.RecursoDto;
import com.senai.sa_gerenciadorrecursos.services.RecursoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }
    @PostMapping("/cadastrarrecurso")
    public String cadastrarRecurso(@Valid @ModelAttribute("recurso") RecursoDto
                                               recursoDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "recursocadastrar";
        }
        recursoService.cadastrarRecurso(recursoDto);
        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Recurso cadastrado com sucesso!");

        return "redirect:/recursolista";
    }
}