package com.app.Cursos.repository;

import com.app.Cursos.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByAnio(Integer anio);

    List<Curso> findByTurnoIgnoreCase(String turno);

    boolean existsByNombreAndAnio(String nombre, Integer anio);
}