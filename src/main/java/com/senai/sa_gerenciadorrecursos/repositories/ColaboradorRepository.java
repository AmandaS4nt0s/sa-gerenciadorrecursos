package com.senai.sa_gerenciadorrecursos.repositories;

import com.senai.sa_gerenciadorrecursos.entities.ColaboradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository <ColaboradorEntity, Long> {

    Optional<ColaboradorEntity> findByEmail(String email);
    Optional<ColaboradorEntity> findByEmailAndSenha(String email, String senha);
}
