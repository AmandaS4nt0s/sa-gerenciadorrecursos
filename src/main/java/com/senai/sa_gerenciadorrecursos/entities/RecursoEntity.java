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
    @Column(name = "segunda_feira")
    private Boolean segundaFeira;
    @Column(name = "terca_feira")
    private Boolean tercaFeira;
    @Column(name = "quarta_feira")
    private Boolean quartaFeira;
    @Column(name = "quinta_feira")
    private Boolean quintaFeira;
    @Column(name = "sexta_feira")
    private Boolean sextaFeira;
    @Column(name = "sabado")
    private Boolean sabado;
    @Column(name = "domingo")
    private Boolean domingo;
    @Column(name = "data_inicio")
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

    public Boolean getSegundaFeira() {
        return segundaFeira;
    }

    public void setSegundaFeira(Boolean segundaFeira) {
        this.segundaFeira = segundaFeira;
    }

    public Boolean getTercaFeira() {
        return tercaFeira;
    }

    public void setTercaFeira(Boolean tercaFeira) {
        this.tercaFeira = tercaFeira;
    }

    public Boolean getQuartaFeira() {
        return quartaFeira;
    }

    public void setQuartaFeira(Boolean quartaFeira) {
        this.quartaFeira = quartaFeira;
    }

    public Boolean getQuintaFeira() {
        return quintaFeira;
    }

    public void setQuintaFeira(Boolean quintaFeira) {
        this.quintaFeira = quintaFeira;
    }

    public Boolean getSextaFeira() {
        return sextaFeira;
    }

    public void setSextaFeira(Boolean sextaFeira) {
        this.sextaFeira = sextaFeira;
    }

    public Boolean getSabado() {
        return sabado;
    }

    public void setSabado(Boolean sabado) {
        this.sabado = sabado;
    }

    public Boolean getDomingo() {
        return domingo;
    }

    public void setDomingo(Boolean domingo) {
        this.domingo = domingo;
    }

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