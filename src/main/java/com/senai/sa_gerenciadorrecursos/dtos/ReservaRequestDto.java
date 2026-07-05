package com.senai.sa_gerenciadorrecursos.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequestDto {

    private Long id;
    @NotNull(message = "O colaborador é obrigatório.")
    private Long colaboradorId;
    @NotNull(message = "O recurso é obrigatório.")
    private Long recursoId;
    @NotNull(message = "A data é obrigatória.")
    private LocalDate data;
    @NotNull(message = "O horário inicial é obrigatório.")
    private LocalTime horaInicio;
    @NotNull(message = "O horário final é obrigatório.")
    private LocalTime horaFim;
    private String observacao;

    public ReservaRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getColaboradorId() {
        return colaboradorId;
    }

    public void setColaboradorId(Long colaboradorId) {
        this.colaboradorId = colaboradorId;
    }

    public Long getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(Long recursoId) {
        this.recursoId = recursoId;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
