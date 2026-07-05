package com.senai.sa_gerenciadorrecursos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private ColaboradorEntity colaborador;
    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    private RecursoEntity recurso;
    @Column(name = "data_reserva")
    @NotNull(message = "A data é obrigatória.")
    private LocalDate data;
    @Column(name = "hora_inicio_reserva")
    private LocalTime horaInicio;
    @Column(name = "hora_fim_reserva")
    private LocalTime horaFim;
    @Column(name = "data_hora_cancelamento")
    private LocalDateTime cancelamento;
    @Column(name = "observacao")
    private String observacao;

    public ReservaEntity() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ColaboradorEntity getColaborador() {
        return colaborador;
    }

    public void setColaborador(ColaboradorEntity colaborador) {
        this.colaborador = colaborador;
    }

    public RecursoEntity getRecurso() {
        return recurso;
    }

    public void setRecurso(RecursoEntity recurso) {
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
        if (data != null && LocalDate.now().isAfter(data.minusDays(1))) {
            throw new IllegalArgumentException("Cancelamento só pode ser feito até 1 dia antes da reserva.");
        }
        this.cancelamento = LocalDateTime.now();
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        if (this.cancelamento != null && (observacao == null || observacao.isBlank())) {
            throw new IllegalArgumentException("Motivo do cancelamento é obrigatório.");
        }
        this.observacao = observacao;
    }
}