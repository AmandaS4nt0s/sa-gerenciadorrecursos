package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ReservaRequestDto;
import com.senai.sa_gerenciadorrecursos.services.ColaboradorService;
import com.senai.sa_gerenciadorrecursos.services.RecursoService;
import com.senai.sa_gerenciadorrecursos.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ReservaController {

    private final ReservaService reservaService;
    private final ColaboradorService colaboradorService;
    private final RecursoService recursoService;

    public ReservaController(ReservaService reservaService, ColaboradorService colaboradorService, RecursoService recursoService) {
        this.reservaService = reservaService;
        this.colaboradorService = colaboradorService;
        this.recursoService = recursoService;
    }

    @PostMapping("/reservacadastrar")
    public String cadastrarReserva(@Valid @ModelAttribute("reserva") ReservaRequestDto reservaRequestDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("listaColaboradores", colaboradorService.listarColaboradores());

            model.addAttribute("listaRecursos", recursoService.listarRecursos());

            return "reservacadastrar";
        }

        try {

            reservaService.cadastrarReserva(reservaRequestDto);

            redirectAttributes.addFlashAttribute("mensagem", "Reserva realizada com sucesso!");

            return "redirect:/reservalista";

        } catch (RuntimeException e) {

            model.addAttribute("erro", e.getMessage());

            model.addAttribute("listaColaboradores", colaboradorService.listarColaboradores());

            model.addAttribute("listaRecursos", recursoService.listarRecursos());

            return "reservacadastrar";
        }
    }

    @PostMapping("/reservacancelar/{id}")
    public String cancelarReserva(@PathVariable Long id, @RequestParam String observacao, RedirectAttributes redirectAttributes) {

        try {

            reservaService.cancelarReserva(id, observacao);

            redirectAttributes.addFlashAttribute("sucesso", "Reserva cancelada com sucesso.");

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/reservalista";
    }
}