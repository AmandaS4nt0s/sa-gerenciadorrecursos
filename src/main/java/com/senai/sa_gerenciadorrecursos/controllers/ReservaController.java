package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ReservaRequestDto;
import com.senai.sa_gerenciadorrecursos.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/reservacadastrar")
    public String cadastrarReserva(@Valid @ModelAttribute("reservas") ReservaRequestDto reservaRequestDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "reservacadastrar";
        }
        reservaService.cadastrarReserva(reservaRequestDto);
        redirectAttributes.addFlashAttribute("mensagem", "Reserva realizada com sucesso!");
        return "redirect:/reservalista";
    }

    @PostMapping("/reservalista")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarReservas());
        return "reservalista";
    }

    @PostMapping("/reservacancelar")
    public String cancelarReserva(Long id, String observacao, RedirectAttributes redirectAttributes) {
        reservaService.cancelarReserva(id, observacao);
        redirectAttributes.addFlashAttribute("mensagem", "Reserva cancelada com sucesso!");
        return "redirect:/reservalista";
    }
}