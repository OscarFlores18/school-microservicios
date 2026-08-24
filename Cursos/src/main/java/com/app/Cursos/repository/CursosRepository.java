package com.app.Cursos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Cursos.domain.Cursos;

public interface CursosRepository extends JpaRepository<Cursos, Long>{

	 List<Cursos> findById_docente(Long id_docente); 
	
}
