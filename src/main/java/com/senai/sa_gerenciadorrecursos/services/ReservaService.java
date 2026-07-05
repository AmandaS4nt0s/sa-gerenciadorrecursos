package com.senai.sa_gerenciadorrecursos.services;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.dtos.RecursoDto;
import com.senai.sa_gerenciadorrecursos.dtos.ReservaRequestDto;
import com.senai.sa_gerenciadorrecursos.dtos.ReservaResponseDto;
import com.senai.sa_gerenciadorrecursos.entities.ColaboradorEntity;
import com.senai.sa_gerenciadorrecursos.entities.RecursoEntity;
import com.senai.sa_gerenciadorrecursos.entities.ReservaEntity;
import com.senai.sa_gerenciadorrecursos.repositories.ColaboradorRepository;
import com.senai.sa_gerenciadorrecursos.repositories.RecursoRepository;
import com.senai.sa_gerenciadorrecursos.repositories.ReservaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final RecursoRepository recursoRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          ColaboradorRepository colaboradorRepository,
                          RecursoRepository recursoRepository) {
        this.reservaRepository = reservaRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.recursoRepository = recursoRepository;
    }

    public ReservaResponseDto cadastrarReserva(ReservaRequestDto reservaRequestDto) {
        validarReserva(reservaRequestDto);

        ReservaEntity reservaEntity = converterReservaRequestDtoParaEntity(reservaRequestDto);

        reservaRepository.save(reservaEntity);

        return converterEntityParaReservaResponseDto(reservaEntity);
    }

    public List<ReservaResponseDto> listarReservas() {
        List<ReservaResponseDto> listaReservas = new ArrayList<>();

        for (ReservaEntity reservaEntity : reservaRepository.findAll()) {
            listaReservas.add(converterEntityParaReservaResponseDto(reservaEntity));
        }

        return listaReservas;
    }

    public ReservaResponseDto buscarPorId(Long id) {

        ReservaEntity reservaEntity = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        return converterEntityParaReservaResponseDto(reservaEntity);
    }

    public ReservaResponseDto cancelarReserva(Long id, String observacao) {

        ReservaEntity reservaEntity = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        if (observacao == null || observacao.isBlank()) {
            throw new RuntimeException("Motivo do cancelamento é obrigatório.");
        }

        if (reservaEntity.getCancelamento() != null) {
            throw new RuntimeException("Esta reserva já foi cancelada.");
        }

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite = reservaEntity.getData()
                .minusDays(1)
                .atStartOfDay();

        if (agora.isAfter(limite)) {
            throw new RuntimeException("Cancelamento só permitido até 1 dia antes da reserva.");
        }

        reservaEntity.setCancelamento(LocalDateTime.now());
        reservaEntity.setObservacao(observacao);

        ReservaEntity reservaCancelada = reservaRepository.save(reservaEntity);

        return converterEntityParaReservaResponseDto(reservaCancelada);
    }

    private void validarReserva(ReservaRequestDto reservaRequestDto) {

        if (reservaRequestDto.getData() == null ||
                reservaRequestDto.getData().isBefore(LocalDate.now())) {
            throw new RuntimeException("A data da reserva não pode estar no passado.");
        }

        if (reservaRequestDto.getHoraInicio() != null &&
                reservaRequestDto.getHoraInicio().isBefore(LocalTime.of(8, 0))) {
            throw new RuntimeException("Horário inicial deve ser a partir das 08h.");
        }

        if (reservaRequestDto.getHoraFim() != null &&
                reservaRequestDto.getHoraFim().isAfter(LocalTime.of(18, 0))) {
            throw new RuntimeException("Horário final deve ser até as 18h.");
        }

        if (reservaRequestDto.getHoraInicio() != null &&
                reservaRequestDto.getHoraFim() != null &&
                reservaRequestDto.getHoraFim().isBefore(reservaRequestDto.getHoraInicio())) {

            throw new RuntimeException("Horário final deve ser maior que o horário inicial.");
        }
    }

    private ReservaEntity converterReservaRequestDtoParaEntity(ReservaRequestDto reservaRequestDto) {

        ReservaEntity reservaEntity = new ReservaEntity();

        ColaboradorEntity colaboradorEntity = colaboradorRepository
                .findById(reservaRequestDto.getColaboradorId())
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado."));

        reservaEntity.setColaborador(colaboradorEntity);

        RecursoEntity recursoEntity = recursoRepository
                .findById(reservaRequestDto.getRecursoId())
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado."));

        reservaEntity.setRecurso(recursoEntity);

        reservaEntity.setData(reservaRequestDto.getData());
        reservaEntity.setHoraInicio(reservaRequestDto.getHoraInicio());
        reservaEntity.setHoraFim(reservaRequestDto.getHoraFim());
        reservaEntity.setObservacao(reservaRequestDto.getObservacao());

        return reservaEntity;
    }

    private ReservaResponseDto converterEntityParaReservaResponseDto(ReservaEntity reservaEntity) {

        ReservaResponseDto reservaResponseDto = new ReservaResponseDto();

        reservaResponseDto.setId(reservaEntity.getId());

        ColaboradorEntity colaboradorEntity = reservaEntity.getColaborador();

        ColaboradorDto colaboradorDto = new ColaboradorDto();
        colaboradorDto.setId(colaboradorEntity.getId());
        colaboradorDto.setNome(colaboradorEntity.getNome());
        colaboradorDto.setEmail(colaboradorEntity.getEmail());
        colaboradorDto.setMatricula(colaboradorEntity.getMatricula());
        colaboradorDto.setDataNascimento(colaboradorEntity.getDataNascimento());

        reservaResponseDto.setColaborador(colaboradorDto);

        RecursoEntity recursoEntity = reservaEntity.getRecurso();

        RecursoDto recursoDto = new RecursoDto();
        recursoDto.setId(recursoEntity.getId());
        recursoDto.setDescricao(recursoEntity.getDescricao());
        recursoDto.setTipo(recursoEntity.getTipo());
        recursoDto.setDiasSemanaDisponiveis(recursoEntity.getDiasSemanaDisponiveis());
        recursoDto.setDataInicio(recursoEntity.getDataInicio());
        recursoDto.setDataFim(recursoEntity.getDataFim());
        recursoDto.setHoraInicio(recursoEntity.getHoraInicio());
        recursoDto.setHoraFim(recursoEntity.getHoraFim());

        reservaResponseDto.setRecurso(recursoDto);

        reservaResponseDto.setData(reservaEntity.getData());
        reservaResponseDto.setHoraInicio(reservaEntity.getHoraInicio());
        reservaResponseDto.setHoraFim(reservaEntity.getHoraFim());
        reservaResponseDto.setCancelamento(reservaEntity.getCancelamento());
        reservaResponseDto.setObservacao(reservaEntity.getObservacao());

        return reservaResponseDto;
    }
}