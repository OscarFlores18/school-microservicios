package com.app.Administracion.mappers;

import com.app.Administracion.domain.Docente;
import com.app.Administracion.domain.DocenteDTO;
import org.springframework.stereotype.Component;

@Component
public class DocenteMapper {

    public DocenteDTO toDTO(Docente docente) {
        if (docente == null) {
            return null;
        }

        return new DocenteDTO(
                docente.getId(),
                docente.getNombre(),
                docente.getApellido(),
                docente.getEmail(),
                docente.getEspecialidad()
        );
    }

    public Docente toEntity(DocenteDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Docente(
                dto.id(),
                dto.nombre(),
                dto.apellido(),
                dto.email(),
                dto.especialidad()
        );
    }

    public void updateEntity(Docente docente, DocenteDTO dto) {
        docente.setNombre(dto.nombre());
        docente.setApellido(dto.apellido());
        docente.setEmail(dto.email());
        docente.setEspecialidad(dto.especialidad());
    }
}
