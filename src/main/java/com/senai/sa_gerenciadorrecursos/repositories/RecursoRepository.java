package com.senai.sa_gerenciadorrecursos.repositories;

import com.senai.sa_gerenciadorrecursos.entities.RecursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecursoRepository extends JpaRepository<RecursoEntity, Long> {

    Optional<RecursoEntity> findById(Long id);
    List<RecursoEntity> findAllByOrderByNomeAsc();
}