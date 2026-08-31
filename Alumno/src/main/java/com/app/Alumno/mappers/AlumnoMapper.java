package com.app.Alumno.mappers;

import com.app.Alumno.domain.Alumno;
import com.app.Alumno.domain.AlumnoDTO;
import com.app.Alumno.domain.Cursos;
import org.springframework.stereotype.Component;

@Component
public class AlumnoMapper {

    public AlumnoDTO toDTO(Alumno alumno) {
        if (alumno == null) {
            return null;
        }

        return new AlumnoDTO(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getEmail(),
                alumno.getDni(),
                alumno.getFechaNacimiento(),
                alumno.getCurso() != null ? alumno.getCurso().getId() : null
        );
    }

    public Alumno toEntity(AlumnoDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Alumno(
                dto.id(),
                dto.nombre(),
                dto.apellido(),
                dto.email(),
                dto.dni(),
                dto.fechaNacimiento(),
                dto.cursoId() != null ? new Cursos(dto.cursoId()) : null
        );
    }

    public void updateEntity(Alumno alumno, AlumnoDTO dto) {
        alumno.setNombre(dto.nombre());
        alumno.setApellido(dto.apellido());
        alumno.setEmail(dto.email());
        alumno.setDni(dto.dni());
        alumno.setFechaNacimiento(dto.fechaNacimiento());
        alumno.setCurso(dto.cursoId() != null ? new Cursos(dto.cursoId()) : null);
    }
}