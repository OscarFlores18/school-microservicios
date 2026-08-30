package com.app.Alumno.mappers;

import com.app.Alumno.domain.Alumno;
import com.app.Alumno.domain.AlumnoDTO;
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
                alumno.getIdCurso()
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
                dto.id_curso()
        );
    }

    public void updateEntity(Alumno alumno, AlumnoDTO dto) {
        alumno.setNombre(dto.nombre());
        alumno.setApellido(dto.apellido());
        alumno.setEmail(dto.email());
        alumno.setDni(dto.dni());
        alumno.setFechaNacimiento(dto.fechaNacimiento());
        alumno.setIdCurso(dto.id_curso());
    }
}