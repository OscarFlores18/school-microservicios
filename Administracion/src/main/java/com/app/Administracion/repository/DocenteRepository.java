package com.app.Administracion.repository;

import com.app.Administracion.domain.DocenteDTO;
import com.app.Administracion.domain.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    boolean existsByEmail(String email);

    Optional<Docente> findByEmail(String email);
}