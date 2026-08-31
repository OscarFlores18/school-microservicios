package com.app.Cursos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.Cursos.domain.Cursos;

public interface CursosRepository extends JpaRepository<Cursos, Long> {

    @Query("SELECT c FROM Curso c WHERE c.docente.id = :idDocente")
    List<Cursos> findByIdDocente(@Param("idDocente") Long idDocente);

}