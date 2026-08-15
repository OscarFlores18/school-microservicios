package com.app.Cursos.service;

import com.app.Cursos.domain.Curso;
import com.app.Cursos.domain.CursoDTO;
import com.app.Cursos.mappers.CursoMapper;
import com.app.Cursos.repository.CursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    public CursoService(CursoRepository cursoRepository, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoMapper = cursoMapper;
    }

    @Transactional(readOnly = true)
    public List<CursoDTO> listarTodos() {
        return cursoRepository.findAll().stream().map(cursoMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<CursoDTO> listarPorAnio(Integer anio) {
        return cursoRepository.findByAnio(anio).stream().map(cursoMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<CursoDTO> listarPorTurno(String turno) {
        return cursoRepository.findByTurnoIgnoreCase(turno).stream().map(cursoMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CursoDTO buscarPorId(Long id) {
        return cursoMapper.toDTO(obtenerEntidad(id));
    }

    public CursoDTO crear(CursoDTO dto) {
        if (cursoRepository.existsByNombreAndAnio(dto.nombre(), dto.anio())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un curso con ese nombre para el año indicado"
            );
        }

        Curso curso = cursoMapper.toEntity(dto);
        curso.setId(null);
        return cursoMapper.toDTO(cursoRepository.save(curso));
    }

    public CursoDTO actualizar(Long id, CursoDTO dto) {
        Curso curso = obtenerEntidad(id);
        cursoMapper.updateEntity(curso, dto);
        return cursoMapper.toDTO(cursoRepository.save(curso));
    }

    public void eliminar(Long id) {
        Curso curso = obtenerEntidad(id);
        cursoRepository.delete(curso);
    }

    private Curso obtenerEntidad(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curso no encontrado con id: " + id
                ));
    }
}