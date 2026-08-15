package com.app.Cursos.controller;

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

import com.app.Cursos.domain.CursoDTO;
import com.app.Cursos.service.CursoService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarTodos(
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) String turno
    ) {
        if (anio != null) {
            return ResponseEntity.ok(cursoService.listarPorAnio(anio));
        }
        if (turno != null && !turno.isBlank()) {
            return ResponseEntity.ok(cursoService.listarPorTurno(turno));
        }
        return ResponseEntity.ok(cursoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CursoDTO> crear(@Valid @RequestBody CursoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cursoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursoDTO dto
    ) {
        return ResponseEntity.ok(cursoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}