package com.senai.sa_gerenciadorrecursos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "colaborador")
    @NotBlank(message = "O colaborador é obrigatório.")
    private Long colaborador;

    @Column(name = "recurso")
    @NotBlank(message = "O recurso é obrigatório.")
    private Long recurso;

    @Column(name = "data_reserva")
    @NotBlank(message = "A data é obrigatória.")
    private LocalDate data;

    @Column(name = "hora_inicio_reserva")
    private LocalTime horaInicio;

    @Column(name = "hora_fim_reserva")
    private LocalTime horaFim;

    @Column(name = "data_hora_cancelamento")
    private LocalDate cancelamento;

    @Column(name = "observacao")
    private String observacao;

    public ReservaEntity() {
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

    public LocalDate getCancelamento() {
        return cancelamento;
    }

    public void setCancelamento(LocalDate cancelamento) {
        this.cancelamento = cancelamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
