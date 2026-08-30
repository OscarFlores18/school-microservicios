package com.app.Cursos.mappers;

import org.springframework.stereotype.Component;

import com.app.Cursos.domain.CursosDTO;
import com.app.model.Cursos;
import com.app.model.Docente;

@Component
public class CursosMappers {

	public CursosDTO toDTO(Cursos curso) {
		if (curso == null) {
			return null;
		}
		return new CursosDTO(
				curso.getId(),
				curso.getNombre(),
				curso.getDescripcion(),
				curso.getCurso(),
				curso.getHorario(),
				curso.getDocente() != null ? curso.getDocente().getId() : null
		);
	}

	public Cursos toEntity(CursosDTO dto) {
		if (dto == null) {
			return null;
		}
		return new Cursos(
				dto.id(),
				dto.nombre(),
				dto.descripcion(),
				dto.curso(),
				dto.horario(),
				dto.id_docente() != null ? new Docente(dto.id_docente()) : null
		);
	}

	public void updateEntity(Cursos curso, CursosDTO dto) {
		curso.setNombre(dto.nombre());
		curso.setDescripcion(dto.descripcion());
		curso.setCurso(dto.curso());
		curso.setHorario(dto.horario());
		curso.setDocente(dto.id_docente() != null ? new Docente(dto.id_docente()) : null);
	}
}