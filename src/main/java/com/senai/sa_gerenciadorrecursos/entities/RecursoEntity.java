package com.senai.sa_gerenciadorrecursos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "recurso")
public class RecursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descricao")
    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;
    @Column(name = "tipo")
    @NotBlank(message = "O tipo é obrigatório.")
    private String tipo;
    @ElementCollection
    @CollectionTable(name = "recurso_dias_semana", joinColumns = @JoinColumn(name = "recurso_id"))
    @Column(name = "dia_semana")
    private List<String> diasSemanaDisponiveis;
    @Column(name = "data_inicio")
    @FutureOrPresent(message = "A data de início não pode estar no passado.")
    private LocalDate dataInicio;
    @Column(name = "data_fim")
    @FutureOrPresent(message = "A data de fim não pode estar no passado.")
    private LocalDate dataFim;
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    @Column(name = "hora_fim")
    private LocalTime horaFim;

    public RecursoEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public List<String> getDiasSemanaDisponiveis() { return diasSemanaDisponiveis; }
    public void setDiasSemanaDisponiveis(List<String> diasSemanaDisponiveis) { this.diasSemanaDisponiveis = diasSemanaDisponiveis; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) {
        if (horaInicio.isBefore(LocalTime.of(8,0))) {
            throw new IllegalArgumentException("Horário inicial deve ser a partir das 08h.");
        }
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) {
        if (horaFim.isAfter(LocalTime.of(18,0))) {
            throw new IllegalArgumentException("Horário final deve ser até as 18h.");
        }
        this.horaFim = horaFim;
    }
}