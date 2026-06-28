package com.senai.sa_gerenciadorrecursos.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaResponseDto {

    private Long id;
    @NotBlank(message = "O colaborador é obrigatório.")
    private Long colaborador;
    @NotBlank(message = "O recurso é obrigatório.")
    private Long recurso;
    @NotBlank(message = "A data é obrigatória.")
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private LocalDateTime cancelamento;

    public ReservaResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getColaborador() {
        return colaborador;
    }

    public void setColaborador(Long colaborador) {
        this.colaborador = colaborador;
    }

    public Long getRecurso() {
        return recurso;
    }

    public void setRecurso(Long recurso) {
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
}
