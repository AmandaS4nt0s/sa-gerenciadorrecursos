package com.senai.sa_gerenciadorrecursos.repositories;

import com.senai.sa_gerenciadorrecursos.entities.ColaboradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository <ColaboradorEntity.Colaborador, Long> {
}
