package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.services.ColaboradorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ColaboradorController {

    private final ColaboradorService colaboradorService;

    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @PostMapping("/cadastrarcolab")
    public String cadastrarColaborador(@Valid @ModelAttribute("colaborador") ColaboradorDto colaboradorDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "colaboradorcadastrar";
        }
        colaboradorService.cadastrarColaborador(colaboradorDto);
        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Colaborador cadastrado com sucesso!");

        return "redirect:/colaboradorlista";
    }
    @PostMapping("/colaboradoratualizar/{id}")
    public String atualizarColaborador(
            @PathVariable Long id,
            @Valid @ModelAttribute("colaborador") ColaboradorDto colaboradorDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "colaboradoratualizar";
        }
        colaboradorService.atualizarColaborador(colaboradorDto, id);
        redirectAttributes.addFlashAttribute("mensagem", "Colaborador atualizado com sucesso!");
        return "redirect:/colaboradorlista";
    }

    @DeleteMapping("/colaboradorexcluir/{id}")
    public ResponseEntity<Void> excluirColaborador(@PathVariable Long id) {
        colaboradorService.excluirColaborador(id);
        return ResponseEntity.noContent().build();
    }
}
