package com.app.Alumno.repository;

import com.app.Alumno.domain.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByDni(String dni);

    List<Alumno> findByCursoNombreIgnoreCase(String nombre);
}