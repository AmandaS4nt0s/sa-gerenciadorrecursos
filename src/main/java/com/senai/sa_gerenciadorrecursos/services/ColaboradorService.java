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
        if (colaboradorRepository.findByEmail(colaboradorDto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
        ColaboradorEntity colaboradorEntity = converterDtoParaEntity(colaboradorDto);
        colaboradorEntity.setSenha(colaboradorDto.getSenha());
        colaboradorRepository.save(colaboradorEntity);
    }

    public ColaboradorDto realizarLogin(ColaboradorDto colaboradorDto){

        System.out.println(colaboradorDto.getEmail());
        System.out.println(colaboradorDto.getSenha());

        Optional<ColaboradorEntity> colaboradorOP = colaboradorRepository.findByEmailAndSenha(colaboradorDto.getEmail(),colaboradorDto.getSenha());
        if (colaboradorOP.isPresent()) {
            return converterEntityParaDto(colaboradorOP.get());
        }
        else {
            throw new RuntimeException("Credenciais inválidas.");
        }
    }

    public void atualizarColaborador(ColaboradorDto colaboradorDto, Long id) {
        ColaboradorEntity colaboradorEntity = colaboradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado"));

        colaboradorEntity.setNome(colaboradorDto.getNome());
        colaboradorEntity.setEmail(colaboradorDto.getEmail());
        colaboradorEntity.setSenha(colaboradorDto.getSenha());
        colaboradorEntity.setMatricula(colaboradorDto.getMatricula());
        colaboradorEntity.setDataNascimento(colaboradorDto.getDataNascimento());

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

    public ColaboradorDto buscarPorId(Long id) {
        ColaboradorEntity colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colaborador não encontrado"));
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