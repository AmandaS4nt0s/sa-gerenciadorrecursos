package com.senai.sa_gerenciadorrecursos.dtos;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RecursoDto {

    private Long id;

    @NotBlank(message = "A Descrição é obrigatória.")
    private String descricao;

    @NotBlank(message = "O Tipo é obrigatório.")
    private String tipo;

    private Boolean segundaFeira;
    private Boolean tercaFeira;
    private Boolean quartaFeira;
    private Boolean quintaFeira;
    private Boolean sextaFeira;
    private Boolean sabado;
    private Boolean domingo;


    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public RecursoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
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
}