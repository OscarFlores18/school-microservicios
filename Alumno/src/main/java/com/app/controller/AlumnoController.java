package com.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.Alumno.domain.AlumnoDTO;
import com.app.Alumno.service.AlumnoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@Tag(name = "Alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> listarTodos(
            @RequestParam(required = false) String curso
    ) {
        if (curso != null && !curso.isBlank()) {
            return ResponseEntity.ok(alumnoService.listarPorCurso(curso));
        }
        return ResponseEntity.ok(alumnoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alumnoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AlumnoDTO> crear(@Valid @RequestBody AlumnoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alumnoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlumnoDTO dto
    ) {
        return ResponseEntity.ok(alumnoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alumnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}