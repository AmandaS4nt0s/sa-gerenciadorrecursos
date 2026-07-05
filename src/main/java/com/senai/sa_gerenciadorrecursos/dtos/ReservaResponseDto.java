package com.senai.sa_gerenciadorrecursos.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaResponseDto {

    private Long id;
    @NotNull(message = "O colaborador é obrigatório.")
    private ColaboradorDto colaborador;
    @NotNull(message = "O recurso é obrigatório.")
    private RecursoDto recurso;
    @NotNull(message = "A data é obrigatória.")
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private LocalDateTime cancelamento;
    private String observacao;

    public ReservaResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ColaboradorDto getColaborador() {
        return colaborador;
    }

    public void setColaborador(ColaboradorDto colaborador) {
        this.colaborador = colaborador;
    }

    public RecursoDto getRecurso() {
        return recurso;
    }

    public void setRecurso(RecursoDto recurso) {
        this.recurso = recurso;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public LocalDateTime getCancelamento() {
        return cancelamento;
    }

    public void setCancelamento(LocalDateTime cancelamento) {
        this.cancelamento = cancelamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
