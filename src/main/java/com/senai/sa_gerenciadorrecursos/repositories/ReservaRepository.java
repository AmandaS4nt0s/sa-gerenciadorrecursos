package com.senai.sa_gerenciadorrecursos.repositories;

import com.senai.sa_gerenciadorrecursos.entities.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    List<ReservaEntity> findByRecursoIdAndData(Long recursoId, LocalDate data);
}