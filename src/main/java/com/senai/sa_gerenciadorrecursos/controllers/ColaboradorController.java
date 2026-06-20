package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.services.ColaboradorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
                "Colaborador cadastrado com sucesso!"
        );

        return "redirect:/colaboradorlista";
    }
    @PostMapping("/atualizarcolab")
    public String atualizarColaborador(Model model, @Valid @ModelAttribute("colaborador") ColaboradorDto colaboradorDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "colaboradoratualizar";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Colaborador atualizado com sucesso!");
        colaboradorService.atualizarColaborador(colaboradorDto);

        return "redirect:/colaboradorlista";
    }
}
