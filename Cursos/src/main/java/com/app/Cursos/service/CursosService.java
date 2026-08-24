package com.app.Cursos.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.Cursos.domain.Cursos;
import com.app.Cursos.domain.CursosDTO;
import com.app.Cursos.mappers.CursosMapper;
import com.app.Cursos.repository.CursosRepository;

@Service
@Transactional
public class CursosService {

    private final CursosRepository cursosRepository;
    private final CursosMapper cursosMapper;

    public CursosService(CursosRepository cursosRepository, CursosMapper cursosMapper) {
        this.cursosRepository = cursosRepository;
        this.cursosMapper = cursosMapper;
    }

    @Transactional(readOnly = true)
    public List<CursosDTO> listarTodos() {
        return cursosRepository.findAll().stream().map(cursosMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CursosDTO buscarPorId(Long id) {
        return cursosMapper.toDTO(obtenerEntidad(id));
    }

    public CursosDTO crear(CursosDTO dto) {

        Cursos curso = cursosMapper.toEntity(dto);
        curso.setId(null);

        return cursosMapper.toDTO(cursosRepository.save(curso));
    }

    public CursosDTO actualizar(Long id, CursosDTO dto) {

        Cursos curso = obtenerEntidad(id);

        cursosMapper.updateEntity(curso, dto);

        return cursosMapper.toDTO(cursosRepository.save(curso));
    }

    public void eliminar(Long id) {

        Cursos curso = obtenerEntidad(id);

        cursosRepository.delete(curso);
    }

    private Cursos obtenerEntidad(Long id) {

        return cursosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curso no encontrado con id: " + id
                ));
    }
}