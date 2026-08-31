package com.app.Alumno.service;

import com.app.Alumno.domain.AlumnoDTO;
import com.app.Alumno.mappers.AlumnoMapper;
import com.app.Alumno.repository.AlumnoRepository;
import com.app.model.Alumno;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;

    public AlumnoService(AlumnoRepository alumnoRepository, AlumnoMapper alumnoMapper) {
        this.alumnoRepository = alumnoRepository;
        this.alumnoMapper = alumnoMapper;
    }

    @Transactional(readOnly = true)
    public List<AlumnoDTO> listarTodos() {
        return alumnoRepository.findAll().stream().map(alumnoMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<AlumnoDTO> listarPorCurso(String curso) {
        return alumnoRepository.findByCursoNombreIgnoreCase(curso).stream().map(alumnoMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public AlumnoDTO buscarPorId(Long id) {
        return alumnoMapper.toDTO(obtenerEntidad(id));
    }

    public AlumnoDTO crear(AlumnoDTO dto) {
        if (alumnoRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un alumno con el email indicado"
            );
        }

        if (alumnoRepository.existsByDni(dto.dni())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un alumno con el DNI indicado"
            );
        }

        Alumno alumno = alumnoMapper.toEntity(dto);
        alumno.setId(null);
        return alumnoMapper.toDTO(alumnoRepository.save(alumno));
    }

    public AlumnoDTO actualizar(Long id, AlumnoDTO dto) {
        Alumno alumno = obtenerEntidad(id);

        alumnoRepository.findByEmail(dto.email())
                .filter(otro -> !otro.getId().equals(id))
                .ifPresent(otro -> {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Ya existe otro alumno con el email indicado"
                    );
                });

        alumnoMapper.updateEntity(alumno, dto);
        return alumnoMapper.toDTO(alumnoRepository.save(alumno));
    }

    public void eliminar(Long id) {
        Alumno alumno = obtenerEntidad(id);
        alumnoRepository.delete(alumno);
    }

    private Alumno obtenerEntidad(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alumno no encontrado con id: " + id
                ));
    }
}