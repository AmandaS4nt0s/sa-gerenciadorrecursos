package com.senai.sa_gerenciadorrecursos.services;

import com.senai.sa_gerenciadorrecursos.dtos.ReservaRequestDto;
import com.senai.sa_gerenciadorrecursos.dtos.ReservaResponseDto;
import com.senai.sa_gerenciadorrecursos.entities.ReservaEntity;
import com.senai.sa_gerenciadorrecursos.repositories.ReservaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public ReservaResponseDto cadastrarReserva(ReservaRequestDto reservaRequestDto) {

        reservaRepository.save(converterReservaRequestDtoParaEntity(reservaRequestDto));
        return new ReservaResponseDto();
    }

    public List<ReservaResponseDto> listarReservas() {
        List<ReservaResponseDto> listaReservas = new ArrayList<>();
        for (ReservaEntity reserva : reservaRepository.findAll()) {
            listaReservas.add(converterEntityParaReservaResponseDto(reserva));
        }
        return listaReservas;
    }

    public ReservaResponseDto buscarPorId(Long id) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        return converterEntityParaReservaResponseDto(reserva);
    }

    public ReservaResponseDto cancelarReserva(Long id, String observacao) {
        ReservaEntity reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        LocalDate hoje = LocalDate.now();
        if (!hoje.isBefore(reserva.getData().minusDays(1))) {
            throw new RuntimeException("Cancelamento só permitido até 1 dia antes da data agendada");
        }
        if (observacao == null || observacao.isBlank()) {
            throw new RuntimeException("Motivo do cancelamento é obrigatório");
        }

        reserva.setCancelamento(hoje);
        reserva.setObservacao(observacao);

        ReservaEntity cancelado = reservaRepository.save(reserva);
        return converterEntityParaReservaResponseDto(cancelado);
    }

    private ReservaEntity converterReservaRequestDtoParaEntity(ReservaRequestDto reservaRequestDto) {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setColaborador(reservaRequestDto.getColaborador());
        reserva.setRecurso(reservaRequestDto.getRecurso());
        reserva.setData(reservaRequestDto.getData());
        reserva.setHoraInicio(reservaRequestDto.getHoraInicio());
        reserva.setHoraFim(reservaRequestDto.getHoraFim());
        reserva.setObservacao(reservaRequestDto.getObservacao());
        return reserva;
    }

    private ReservaResponseDto converterEntityParaReservaResponseDto(ReservaEntity reserva) {
        ReservaResponseDto reservaResponseDto = new ReservaResponseDto();
        reservaResponseDto.setId(reserva.getId());
        reservaResponseDto.setColaborador(reserva.getColaborador());
        reservaResponseDto.setRecurso(reserva.getRecurso());
        reservaResponseDto.setData(reserva.getData());
        reservaResponseDto.setHoraInicio(reserva.getHoraInicio());
        reservaResponseDto.setHoraFim(reserva.getHoraFim());
        reservaResponseDto.setCancelamento(reserva.getCancelamento());
        reservaResponseDto.setObservacao(reserva.getObservacao());
        return reservaResponseDto;
    }
}