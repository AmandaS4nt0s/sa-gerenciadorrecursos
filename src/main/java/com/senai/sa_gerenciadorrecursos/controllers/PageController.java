package com.senai.sa_gerenciadorrecursos.controllers;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.dtos.RecursoDto;
import com.senai.sa_gerenciadorrecursos.dtos.ReservaRequestDto;
import com.senai.sa_gerenciadorrecursos.services.ColaboradorService;
import com.senai.sa_gerenciadorrecursos.services.RecursoService;
import com.senai.sa_gerenciadorrecursos.services.ReservaService;
import com.senai.sa_gerenciadorrecursos.sessao.SessaoDto;
import com.senai.sa_gerenciadorrecursos.sessao.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

    private final ColaboradorService colaboradorService;
    private final RecursoService recursoService;
    private final ReservaService reservaService;

    public PageController(ColaboradorService colaboradorService, RecursoService recursoService, ReservaService reservaService) {
        this.colaboradorService = colaboradorService;
        this.recursoService = recursoService;
        this.reservaService = reservaService;
    }

    @GetMapping("/")
    public String getLogin(HttpSession session) {
        return "login";
    }

    @GetMapping("/home")
    public String getHome(Model model, HttpSession session){

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/";
        }
        model.addAttribute("usuarioLogado",sessaoDto);
        return "home";
    }

    //-------------------------- COLABORADOR --------------------------
    @GetMapping("/colaboradorcadastrar")
    public String colaboradorCadastrar(Model model) {


        model.addAttribute("colaborador", new ColaboradorDto());
        return "colaboradorcadastrar";
    }

    @GetMapping("/colaboradorlista")
    public String colaboradorLista(Model model,HttpSession session) {


        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);

        if (sessaoDto == null){
            return "redirect:/";
        }
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
    public String recursoLista(Model model){
        model.addAttribute("recurso", recursoService.listarRecursos());
        return "recursolista";
    }
    @GetMapping("/recursoatualizar/{id}")
    public String atualizarRecurso(@PathVariable Long id, Model model) {
        RecursoDto recurso = recursoService.buscarPorId(id);
        model.addAttribute("recurso", recurso);
        return "recursoatualizar";
    }
    //-------------------------- RESERVA --------------------------
    @GetMapping("/reservacadastrar")
    public String cadastrarReserva(Model model) {
        model.addAttribute("reserva", new ReservaRequestDto());
        return "reservacadastrar";
    }
    @GetMapping("/reservalista")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarReservas());
        return "reservalista";
    }
    @GetMapping("/reservacancelar")
    public String cancelarReserva(Long id, String observacao, RedirectAttributes redirectAttributes) {
        reservaService.cancelarReserva(id, observacao);
        redirectAttributes.addFlashAttribute("mensagem", "Reserva cancelada com sucesso!");
        return "redirect:/reservalista";
    }
}