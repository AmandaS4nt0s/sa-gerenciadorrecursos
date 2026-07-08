package com.senai.sa_gerenciadorrecursos.services;

import com.senai.sa_gerenciadorrecursos.dtos.RecursoDto;
import com.senai.sa_gerenciadorrecursos.entities.RecursoEntity;
import com.senai.sa_gerenciadorrecursos.repositories.RecursoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    public void cadastrarRecurso(RecursoDto recursoDto){
        validarRecurso(recursoDto);
        recursoRepository.save(converterDtoParaEntity(recursoDto));
    }

    public void atualizarRecurso(RecursoDto recursoDto, Long id) {
        RecursoEntity recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        validarRecurso(recursoDto);

        recurso.setDescricao(recursoDto.getDescricao());
        recurso.setTipo(recursoDto.getTipo());
        recurso.setSegundaFeira(recursoDto.getSegundaFeira());
        recurso.setTercaFeira(recursoDto.getTercaFeira());
        recurso.setQuartaFeira(recursoDto.getQuartaFeira());
        recurso.setQuintaFeira(recursoDto.getQuintaFeira());
        recurso.setSextaFeira(recursoDto.getSextaFeira());
        recurso.setSabado(recursoDto.getSabado());
        recurso.setDomingo(recursoDto.getDomingo());
        recurso.setDataInicio(recursoDto.getDataInicio());
        recurso.setDataFim(recursoDto.getDataFim());
        recurso.setHoraInicio(recursoDto.getHoraInicio());
        recurso.setHoraFim(recursoDto.getHoraFim());

        recursoRepository.save(recurso);
    }

    public List<RecursoDto> listarRecursos() {
        List<RecursoDto> listaRecursos = new ArrayList<>();
        for (RecursoEntity recurso : recursoRepository.findAll()) {
            listaRecursos.add(converterEntityParaDto(recurso));
        }
        return listaRecursos;
    }

    public RecursoDto buscarPorId(Long id) {
        RecursoEntity recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado"));
        return converterEntityParaDto(recurso);
    }

    public void excluirRecurso(Long id) {
        recursoRepository.deleteById(id);
    }

    // Validações do RF02
    private void validarRecurso(RecursoDto recursoDto) {
        if (recursoDto.getDescricao() == null || recursoDto.getDescricao().isBlank()) {
            throw new RuntimeException("A descrição do recurso é obrigatória.");
        }
        if (recursoDto.getTipo() == null || recursoDto.getTipo().isBlank()) {
            throw new RuntimeException("O tipo do recurso é obrigatório.");
        }
        if (recursoDto.getDataFim() != null && recursoDto.getDataInicio() != null &&
                recursoDto.getDataFim().isBefore(recursoDto.getDataInicio())) {
            throw new RuntimeException("A data final não pode ser anterior à data inicial.");
        }
        if (recursoDto.getHoraInicio() != null && recursoDto.getHoraFim() != null &&
                recursoDto.getHoraFim().isBefore(recursoDto.getHoraInicio())) {
            throw new RuntimeException("Horário final deve ser maior que o horário inicial.");
        }
    }

    private RecursoDto converterEntityParaDto(RecursoEntity recurso){
        RecursoDto recursoDto = new RecursoDto();
        recursoDto.setId(recurso.getId());
        recursoDto.setDescricao(recurso.getDescricao());
        recursoDto.setTipo(recurso.getTipo());
        recursoDto.setSegundaFeira(recurso.getSegundaFeira());
        recursoDto.setTercaFeira(recurso.getTercaFeira());
        recursoDto.setQuartaFeira(recurso.getQuartaFeira());
        recursoDto.setQuintaFeira(recurso.getQuintaFeira());
        recursoDto.setSextaFeira(recurso.getSextaFeira());
        recursoDto.setSabado(recurso.getSabado());
        recursoDto.setDomingo(recurso.getDomingo());
        recursoDto.setDataInicio(recurso.getDataInicio());
        recursoDto.setDataFim(recurso.getDataFim());
        recursoDto.setHoraInicio(recurso.getHoraInicio());
        recursoDto.setHoraFim(recurso.getHoraFim());
        return recursoDto;
    }

    private RecursoEntity converterDtoParaEntity(RecursoDto recursoDto){
        RecursoEntity recurso = new RecursoEntity();
        recurso.setDescricao(recursoDto.getDescricao());
        recurso.setTipo(recursoDto.getTipo());
        recurso.setSegundaFeira(recursoDto.getSegundaFeira());
        recurso.setTercaFeira(recursoDto.getTercaFeira());
        recurso.setQuartaFeira(recursoDto.getQuartaFeira());
        recurso.setQuintaFeira(recursoDto.getQuintaFeira());
        recurso.setSextaFeira(recursoDto.getSextaFeira());
        recurso.setSabado(recursoDto.getSabado());
        recurso.setDomingo(recursoDto.getDomingo());
        recurso.setDataInicio(recursoDto.getDataInicio());
        recurso.setDataFim(recursoDto.getDataFim());
        recurso.setHoraInicio(recursoDto.getHoraInicio());
        recurso.setHoraFim(recursoDto.getHoraFim());
        return recurso;
    }
}
