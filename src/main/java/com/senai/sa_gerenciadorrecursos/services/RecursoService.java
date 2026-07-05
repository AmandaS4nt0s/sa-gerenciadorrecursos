package com.senai.sa_gerenciadorrecursos.services;

import com.senai.sa_gerenciadorrecursos.dtos.RecursoDto;
import com.senai.sa_gerenciadorrecursos.entities.ColaboradorEntity;
import com.senai.sa_gerenciadorrecursos.entities.RecursoEntity;
import com.senai.sa_gerenciadorrecursos.repositories.ColaboradorRepository;
import com.senai.sa_gerenciadorrecursos.repositories.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {

        this.recursoRepository = recursoRepository;
    }
    public void cadastrarRecurso(RecursoDto recursoDto){
        recursoRepository.save(converterDtoParaEntity(recursoDto));
    }

    public List<RecursoDto> listarRecursos() {
        List<RecursoDto> listaRecursos = new ArrayList<>();

        for (RecursoEntity recurso : recursoRepository.findAll()) {
            listaRecursos.add(converterEntityParaDto(recurso));
        }
        return listaRecursos;
    }

    public void atualizarRecurso(RecursoDto recursoDto, Long id) {

        RecursoEntity recurso = recursoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Recurso não encontrado"));

        recurso.setDescricao(recursoDto.getDescricao());
        recurso.setTipo(recursoDto.getTipo());
        recurso.setDiaSemana(recursoDto.getDiaSemana());
        recurso.setDataInicio(recursoDto.getDataInicio());
        recurso.setDataFim(recursoDto.getDataFim());
        recurso.setHoraInicio(recursoDto.getHoraInicio());
        recurso.setHoraFim(recursoDto.getHoraFim());

        recursoRepository.save(recurso);
    }

    public RecursoDto buscarPorId(Long id) {
        RecursoEntity recurso = recursoRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Recurso não encontrado"));
        return converterEntityParaDto(recurso);
    }

    public void excluirRecurso(Long id) {
        recursoRepository.deleteById(id);
    }

    private RecursoDto converterEntityParaDto(RecursoEntity recurso){
        RecursoDto recursoDto = new RecursoDto();
        recursoDto.setDescricao(recurso.getDescricao());
        recursoDto.setTipo(recurso.getTipo());
        recursoDto.setDiaSemana(recurso.getDiaSemana());
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
        recurso.setDiaSemana(recursoDto.getDiaSemana());
        recurso.setDataInicio(recursoDto.getDataInicio());
        recurso.setDataFim(recursoDto.getDataFim());
        recurso.setHoraInicio(recursoDto.getHoraInicio());
        recurso.setHoraFim(recursoDto.getHoraFim());
        return recurso;
    }
    @Autowired
    private RecursoRepository repo;

    public List<RecursoEntity> listarTodosRecursos() {
        return repo.findAllByOrderByNomeAsc();
    }
    public Optional<RecursoEntity> buscarRecursoPorId(Long id) {
        return repo.findById(id);
    }
}
