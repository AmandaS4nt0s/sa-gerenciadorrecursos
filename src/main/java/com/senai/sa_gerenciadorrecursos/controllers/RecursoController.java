package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.RecursoDto;
import com.senai.sa_gerenciadorrecursos.services.RecursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {

        this.recursoService = recursoService;
    }

    @PostMapping("/recursocadastrar")
    public String cadastrarRecurso(@Valid @ModelAttribute("recursos") RecursoDto recursoDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "recursocadastrar";
        }
        recursoService.cadastrarRecurso(recursoDto);
        redirectAttributes.addFlashAttribute("mensagem", "Recurso cadastrado com sucesso!");
        return "redirect:/recursolista";
    }

    @PostMapping("/recursoatualizar/{id}")
    public String atualizarRecurso(Model model, @PathVariable Long id, @Valid @ModelAttribute("recurso") RecursoDto recursoDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "recursoatualizar";
        }
        recursoService.atualizarRecurso(recursoDto, id);
        redirectAttributes.addFlashAttribute("mensagem", "Recurso atualizado com sucesso!");
        return "redirect:/recursolista";
    }
    @DeleteMapping("/recursoexcluir/{id}")
    public ResponseEntity<String> excluirRecurso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        recursoService.excluirRecurso(id);
        return ResponseEntity.ok().body("Excluido");
    }
}
