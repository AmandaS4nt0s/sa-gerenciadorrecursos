package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.services.ColaboradorService;
import com.senai.sa_gerenciadorrecursos.sessao.SessaoDto;
import com.senai.sa_gerenciadorrecursos.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
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
    public String realizarLogin(String email, String senha, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        try {
            //--Criação de objeto dto para enviara dados para o service
            ColaboradorDto colaboradorDto = new ColaboradorDto();
            colaboradorDto.setEmail(email);
            colaboradorDto.setSenha(senha);

            //--Realiza login no banco de dados
            ColaboradorDto colaboradorDtoRetorno = colaboradorService.realizarLogin(colaboradorDto);

            //--Verifica se retonar dados do usuário significa que deu certo
            if (colaboradorDtoRetorno.getNome() != null) {

                SessaoDto sessaoDto = new SessaoDto();
                sessaoDto.setUsuarioid(colaboradorDtoRetorno.getId());
                sessaoDto.setUsuarioNome(colaboradorDto.getNome());
                SessaoUtil.RegistrarSessao(session, sessaoDto);

                redirectAttributes.addFlashAttribute("usuario", " Bem-vindo " + colaboradorDtoRetorno.getNome());
                return "redirect:/home";
            }

        } catch (RuntimeException e) {
            //-- retorna erro no login
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            return "login";
        }
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        SessaoUtil.RemoverSessao(session);
        return "redirect:/login";
    }

    @PostMapping("/colaboradorcadastrar")
    public String cadastrarColaborador(
            @Valid @ModelAttribute("colaborador") ColaboradorDto colaboradorDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "colaboradorcadastrar";
        }

        try {
            colaboradorService.cadastrarColaborador(colaboradorDto);

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Colaborador cadastrado com sucesso!");

            return "redirect:/colaboradorlista";

        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "colaboradorcadastrar";
        }
    }

    @PostMapping("/colaboradoratualizar/{id}")
    public String atualizarColaborador(Model model, @PathVariable Long id, @Valid @ModelAttribute("colaborador") ColaboradorDto
            colaboradorDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "colaboradoratualizar";
        }
        redirectAttributes.addFlashAttribute("mensagem", "Colaborador atualizado com sucesso!");
        colaboradorService.atualizarColaborador(colaboradorDto,id);
        return "redirect:/colaboradorlista";
    }

    @DeleteMapping("/colaboradorexcluir/{id}")
    public ResponseEntity<String> excluirColaborador(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        colaboradorService.excluirColaborador(id);
        return ResponseEntity.ok().body("Excluido");
    }
}
