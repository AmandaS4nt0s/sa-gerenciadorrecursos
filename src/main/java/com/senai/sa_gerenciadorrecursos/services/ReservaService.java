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

import java.time.DayOfWeek;
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

    public ReservaService(ReservaRepository reservaRepository, ColaboradorRepository colaboradorRepository, RecursoRepository recursoRepository) {
        this.reservaRepository = reservaRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.recursoRepository = recursoRepository;
    }

    public ReservaResponseDto cadastrarReserva(ReservaRequestDto reservaRequestDto) {

        RecursoEntity recurso = recursoRepository.findById(reservaRequestDto.getRecursoId()).orElseThrow(() -> new RuntimeException("Recurso não encontrado."));

        validarReserva(reservaRequestDto, recurso);

        validarDisponibilidade(reservaRequestDto, recurso);

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

        ReservaEntity reservaEntity = reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        return converterEntityParaReservaResponseDto(reservaEntity);
    }

    public ReservaResponseDto cancelarReserva(Long id, String observacao) {

        ReservaEntity reservaEntity = reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        if (observacao == null || observacao.isBlank()) {
            throw new RuntimeException("Motivo do cancelamento é obrigatório.");
        }

        if (reservaEntity.getCancelamento() != null) {
            throw new RuntimeException("Esta reserva já foi cancelada.");
        }

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite = reservaEntity.getData().minusDays(1).atStartOfDay();

        if (agora.isAfter(limite)) {
            throw new RuntimeException("Cancelamento só permitido até 1 dia antes da reserva.");
        }

        reservaEntity.setCancelamento(LocalDateTime.now());
        reservaEntity.setObservacao(observacao);

        ReservaEntity reservaCancelada = reservaRepository.save(reservaEntity);

        return converterEntityParaReservaResponseDto(reservaCancelada);
    }

    private void validarReserva(ReservaRequestDto dto, RecursoEntity recurso) {

        if (dto.getData() == null) {
            throw new RuntimeException("Informe a data da reserva.");
        }

        if (dto.getHoraInicio() == null || dto.getHoraFim() == null) {
            throw new RuntimeException("Informe horário inicial e final.");
        }

        if (dto.getData().isBefore(LocalDate.now())) {
            throw new RuntimeException("A data da reserva não pode estar no passado.");
        }

        if (dto.getData().equals(LocalDate.now()) && dto.getHoraInicio().isBefore(LocalTime.now())) {

            throw new RuntimeException("Não é possível reservar um horário que já passou.");
        }

        if (!dto.getHoraFim().isAfter(dto.getHoraInicio())) {
            throw new RuntimeException("O horário final deve ser maior que o horário inicial.");
        }

        if (dto.getData().isBefore(recurso.getDataInicio()) || dto.getData().isAfter(recurso.getDataFim())) {

            throw new RuntimeException("O recurso não está disponível nesta data.");
        }
    }

    private void validarDiaDisponivel(RecursoEntity recurso, DayOfWeek dia) {

        boolean disponivel = switch (dia) {

            case MONDAY -> Boolean.TRUE.equals(recurso.getSegundaFeira());
            case TUESDAY -> Boolean.TRUE.equals(recurso.getTercaFeira());
            case WEDNESDAY -> Boolean.TRUE.equals(recurso.getQuartaFeira());
            case THURSDAY -> Boolean.TRUE.equals(recurso.getQuintaFeira());
            case FRIDAY -> Boolean.TRUE.equals(recurso.getSextaFeira());
            case SATURDAY -> Boolean.TRUE.equals(recurso.getSabado());
            case SUNDAY -> Boolean.TRUE.equals(recurso.getDomingo());

        };

        if (!disponivel) {
            throw new RuntimeException("Este recurso não está disponível neste dia da semana.");
        }
    }

    private void validarDisponibilidade(ReservaRequestDto dto, RecursoEntity recurso) {

        DayOfWeek dia = dto.getData().getDayOfWeek();

        validarDiaDisponivel(recurso, dia);

        if (dto.getHoraInicio().isBefore(recurso.getHoraInicio()) || dto.getHoraFim().isAfter(recurso.getHoraFim())) {

            throw new RuntimeException("O recurso está disponível apenas das " + recurso.getHoraInicio() + " às " + recurso.getHoraFim() + ".");
        }

        List<ReservaEntity> reservasExistentes = reservaRepository.findByRecursoIdAndData(recurso.getId(), dto.getData());

        for (ReservaEntity reserva : reservasExistentes) {

            // Ignora reservas canceladas
            if (reserva.getCancelamento() != null) {
                continue;
            }

            boolean conflito = dto.getHoraInicio().isBefore(reserva.getHoraFim()) && dto.getHoraFim().isAfter(reserva.getHoraInicio());

            if (conflito) {

                String sugestao = encontrarSugestoesHorario(recurso, dto.getData(), dto.getHoraInicio());

                throw new RuntimeException("Este recurso já está reservado neste horário.\n\n" + sugestao);
            }
        }
    }

    private String encontrarSugestoesHorario(RecursoEntity recurso, LocalDate data, LocalTime horaSolicitada) {


        List<ReservaEntity> reservas = reservaRepository.findByRecursoIdAndData(recurso.getId(), data);
        List<LocalTime[]> horariosLivres = new ArrayList<>();
        LocalTime inicio = recurso.getHoraInicio();

        while (inicio.plusHours(1).isBefore(recurso.getHoraFim()) || inicio.plusHours(1).equals(recurso.getHoraFim())) {

            LocalTime fim = inicio.plusHours(1);
            LocalTime inicioTeste = inicio;
            LocalTime fimTeste = fim;
            boolean ocupado = reservas.stream().anyMatch(r -> inicioTeste.isBefore(r.getHoraFim()) && fimTeste.isAfter(r.getHoraInicio()));
            if (!ocupado) {

                horariosLivres.add(new LocalTime[]{inicio, fim});
            }
            inicio = inicio.plusHours(1);
        }
        if (horariosLivres.isEmpty()) {

            return "Não existem horários disponíveis neste dia.";
        }


        // Ordena pelos horários mais próximos do solicitado
        horariosLivres.sort((h1, h2) -> {

            long distancia1 = Math.abs(h1[0].toSecondOfDay() - horaSolicitada.toSecondOfDay());


            long distancia2 = Math.abs(h2[0].toSecondOfDay() - horaSolicitada.toSecondOfDay());


            return Long.compare(distancia1, distancia2);
        });


        StringBuilder mensagem = new StringBuilder();

        mensagem.append("Horários disponíveis próximos:");

        horariosLivres.stream().limit(3).forEach(h -> mensagem.append("\n- ").append(h[0]).append(" às ").append(h[1]));

        return mensagem.toString();
    }

    private ReservaEntity converterReservaRequestDtoParaEntity(ReservaRequestDto reservaRequestDto) {

        ReservaEntity reservaEntity = new ReservaEntity();

        ColaboradorEntity colaboradorEntity = colaboradorRepository.findById(reservaRequestDto.getColaboradorId()).orElseThrow(() -> new RuntimeException("Colaborador não encontrado."));

        reservaEntity.setColaborador(colaboradorEntity);

        RecursoEntity recursoEntity = recursoRepository.findById(reservaRequestDto.getRecursoId()).orElseThrow(() -> new RuntimeException("Recurso não encontrado."));

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

        RecursoEntity recursoEntity = reservaEntity.getRecurso();

        RecursoDto recursoDto = new RecursoDto();
        recursoDto.setId(recursoEntity.getId());
        recursoDto.setDescricao(recursoEntity.getDescricao());
        recursoDto.setTipo(recursoEntity.getTipo());
        recursoDto.setDataInicio(recursoEntity.getDataInicio());
        recursoDto.setDataFim(recursoEntity.getDataFim());
        recursoDto.setHoraInicio(recursoEntity.getHoraInicio());
        recursoDto.setHoraFim(recursoEntity.getHoraFim());

        reservaResponseDto.setData(reservaEntity.getData());
        reservaResponseDto.setHoraInicio(reservaEntity.getHoraInicio());
        reservaResponseDto.setHoraFim(reservaEntity.getHoraFim());
        reservaResponseDto.setCancelamento(reservaEntity.getCancelamento());
        reservaResponseDto.setObservacao(reservaEntity.getObservacao());

        reservaResponseDto.setNomeColaborador(reservaEntity.getColaborador().getNome());
        reservaResponseDto.setDescricaoRecurso(reservaEntity.getRecurso().getDescricao());
        return reservaResponseDto;
    }
}