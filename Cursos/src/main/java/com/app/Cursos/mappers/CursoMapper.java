package com.app.Cursos.mappers;

import com.app.Cursos.domain.Curso;
import com.app.Cursos.domain.CursoDTO;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

    public CursoDTO toDTO(Curso curso) {
        if (curso == null) {
            return null;
        }

        return new CursoDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getAnio(),
                curso.getTurno(),
                curso.getCupoMaximo()
        );
    }

    public Curso toEntity(CursoDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Curso(
                dto.id(),
                dto.nombre(),
                dto.descripcion(),
                dto.anio(),
                dto.turno(),
                dto.cupoMaximo()
        );
    }

    public void updateEntity(Curso curso, CursoDTO dto) {
        curso.setNombre(dto.nombre());
        curso.setDescripcion(dto.descripcion());
        curso.setAnio(dto.anio());
        curso.setTurno(dto.turno());
        curso.setCupoMaximo(dto.cupoMaximo());
    }
}