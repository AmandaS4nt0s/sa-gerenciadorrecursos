package com.senai.sa_gerenciadorrecursos.services;

import com.senai.sa_gerenciadorrecursos.dtos.ColaboradorDto;
import com.senai.sa_gerenciadorrecursos.entities.ColaboradorEntity;
import com.senai.sa_gerenciadorrecursos.repositories.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    public ColaboradorService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }
    public void cadastrarColaborador(ColaboradorDto colaboradorDto){
        colaboradorRepository.save(converterDtoParaEntity(colaboradorDto));
    }

    public ColaboradorDto realizarLogin(ColaboradorDto colaboradorDto){

        Optional<ColaboradorEntity> colaboradorOP = colaboradorRepository.findByEmailAndSenha(colaboradorDto.getEmail(), colaboradorDto.getSenha());
        ColaboradorDto colaboradorDtoRetorno = new ColaboradorDto();

        if (colaboradorOP.isPresent()){
            colaboradorDtoRetorno = converterEntityParaDto(colaboradorOP.get());
            return colaboradorDtoRetorno;
        }
        return colaboradorDtoRetorno;
    }

    public void atualizarColaborador(ColaboradorDto colaboradorDto, String email) {

        ColaboradorEntity colaboradorEntity = colaboradorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado"));

        colaboradorEntity.setNome(colaboradorDto.getNome());
        colaboradorEntity.setEmail(colaboradorDto.getEmail());

        if (colaboradorDto.getSenha() != null && !colaboradorDto.getSenha().isEmpty()) {
            colaboradorEntity.setSenha(colaboradorDto.getSenha());
        }
        colaboradorRepository.save(colaboradorEntity);
    }

    public List<ColaboradorDto> listarColaboradores() {
        List<ColaboradorDto> listaColaboradores = new ArrayList<>();

        for (ColaboradorEntity colaborador : colaboradorRepository.findAll()) {
            listaColaboradores.add(converterEntityParaDto(colaborador));
        }
        return listaColaboradores;
    }

    public ColaboradorDto buscarPorEmail(String email) {
        ColaboradorEntity colaborador = colaboradorRepository.findByEmail(email).orElseThrow(() ->
                        new RuntimeException("Colaborador não encontrado"));
        return converterEntityParaDto(colaborador);
    }

    public void excluirColaborador(Long id) {
        colaboradorRepository.deleteById(id);
    }

    private ColaboradorDto converterEntityParaDto(ColaboradorEntity colaborador){
        ColaboradorDto colaboradorDto = new ColaboradorDto();
        colaboradorDto.setId(colaborador.getId());
        colaboradorDto.setNome(colaborador.getNome());
        colaboradorDto.setEmail(colaborador.getEmail());
        colaboradorDto.setSenha(colaborador.getSenha());
        colaboradorDto.setMatricula(colaborador.getMatricula());
        colaboradorDto.setDataNascimento(colaborador.getDataNascimento());
        return colaboradorDto;
    }

    private ColaboradorEntity converterDtoParaEntity(ColaboradorDto colaboradorDto){
        ColaboradorEntity colaborador = new ColaboradorEntity();
        colaborador.setId(colaboradorDto.getId());
        colaborador.setNome(colaboradorDto.getNome());
        colaborador.setEmail(colaboradorDto.getEmail());
        colaborador.setSenha(colaboradorDto.getSenha());
        colaborador.setMatricula(colaboradorDto.getMatricula());
        colaborador.setDataNascimento(colaboradorDto.getDataNascimento());
        return colaborador;
    }
}
