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
        recurso.setDiasSemanaDisponiveis(recursoDto.getDiasSemanaDisponiveis());
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
        if (recursoDto.getDataInicio() != null && recursoDto.getDataInicio().isBefore(LocalDate.now())) {
            throw new RuntimeException("A data de início não pode estar no passado.");
        }
        if (recursoDto.getDataFim() != null && recursoDto.getDataInicio() != null &&
                recursoDto.getDataFim().isBefore(recursoDto.getDataInicio())) {
            throw new RuntimeException("A data final não pode ser anterior à data inicial.");
        }
        if (recursoDto.getHoraInicio() != null && recursoDto.getHoraInicio().isBefore(LocalTime.of(8,0))) {
            throw new RuntimeException("Horário inicial deve ser a partir das 08h.");
        }
        if (recursoDto.getHoraFim() != null && recursoDto.getHoraFim().isAfter(LocalTime.of(18,0))) {
            throw new RuntimeException("Horário final deve ser até as 18h.");
        }
        if (recursoDto.getHoraInicio() != null && recursoDto.getHoraFim() != null &&
                recursoDto.getHoraFim().isBefore(recursoDto.getHoraInicio())) {
            throw new RuntimeException("Horário final deve ser maior que o horário inicial.");
        }
        if (recursoDto.getDiasSemanaDisponiveis() != null && !recursoDto.getDiasSemanaDisponiveis().isEmpty()) {
            List<String> diasValidos = List.of("SEGUNDA","TERÇA","QUARTA","QUINTA","SEXTA","SÁBADO","DOMINGO");
            for (String dia : recursoDto.getDiasSemanaDisponiveis()) {
                if (!diasValidos.contains(dia.toUpperCase())) {
                    throw new RuntimeException("Dia da semana inválido: " + dia + ". Use apenas: " + diasValidos);
                }
            }
        }
    }

    private RecursoDto converterEntityParaDto(RecursoEntity recurso){
        RecursoDto recursoDto = new RecursoDto();
        recursoDto.setDescricao(recurso.getDescricao());
        recursoDto.setTipo(recurso.getTipo());
        recursoDto.setDiasSemanaDisponiveis(recurso.getDiasSemanaDisponiveis());
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
        recurso.setDiasSemanaDisponiveis(recursoDto.getDiasSemanaDisponiveis());
        recurso.setDataInicio(recursoDto.getDataInicio());
        recurso.setDataFim(recursoDto.getDataFim());
        recurso.setHoraInicio(recursoDto.getHoraInicio());
        recurso.setHoraFim(recursoDto.getHoraFim());
        return recurso;
    }
}
