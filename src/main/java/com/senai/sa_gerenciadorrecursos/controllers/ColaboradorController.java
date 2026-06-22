package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.services.ColaboradorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public String realizarLogin(String email, String senha, Model model, RedirectAttributes redirectAttributes) {

        //--Criação de objeto dto para enviara dados para o service
        ColaboradorDto colaboradorDto = new ColaboradorDto();
        colaboradorDto.setEmail(email);
        colaboradorDto.setSenha(senha);

        //--Realiza login no banco de dados
        ColaboradorDto colaboradorDtoRetorno = colaboradorService.realizarLogin(colaboradorDto);

        //--Verifica se retonar dados do usuário significa que deu certo
        if (colaboradorDtoRetorno.getNome() != null) {

            redirectAttributes.addFlashAttribute("usuario", " Bem-vindo " + colaboradorDtoRetorno.getNome());
            return "redirect:/home";

        }
        //-- retorna erro no login
        model.addAttribute("erro","E-mail ou senha invalidos.");
        return "login";
    }

    @PostMapping("/cadastrarcolab")
    public String cadastrarColaborador(@Valid @ModelAttribute("colaborador") ColaboradorDto
                                               colaboradorDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "colaboradorcadastrar";
        }
        colaboradorService.cadastrarColaborador(colaboradorDto);
        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Colaborador cadastrado com sucesso!");

        return "redirect:/colaboradorlista";
    }

    @PostMapping("/colaboradoratualizar/{email}")
    public String atualizarColaborador(Model model, @PathVariable String email, @Valid @ModelAttribute("colaborador") ColaboradorDto
            colaboradorDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "colaboradoratualizar";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Colaborador atualizado com sucesso!");
        colaboradorService.atualizarColaborador(colaboradorDto,email);
        return "redirect:/colaboradorlista";
    }

    @DeleteMapping("/colaboradorexcluir/{id}")
    public ResponseEntity<String> excluirColaborador(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        colaboradorService.excluirColaborador(id);
        return ResponseEntity.ok().body("Excluido");
    }
}