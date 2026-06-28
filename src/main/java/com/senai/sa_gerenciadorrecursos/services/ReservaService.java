package com.senai.sa_gerenciadorrecursos.services;

import com.senai.sa_gerenciadorrecursos.dtos.ReservaRequestDto;
import com.senai.sa_gerenciadorrecursos.entities.ReservaEntity;

public class ReservaService {

    private ReservaRequestDto converterEntityParaDto(ReservaEntity reserva){
        ReservaRequestDto reservaRequestDto = new ReservaRequestDto();
        reservaRequestDto.setId(reserva.getId());

        return reservaRequestDto;
    }

    private ReservaEntity converterDtoParaEntity(ReservaRequestDto reservaRequestDto){
        ReservaEntity reserva = new ReservaEntity();
        reserva.setId(reservaRequestDto.getId());
        reserva.setColaborador(reservaRequestDto.getId();
        return reserva;
    }
}
