package com.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.app.Cursos.domain.CursosDTO;
import com.app.Cursos.service.CursosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cursos")
public class CursosController {
    private final CursosService cursosService;

    public CursosController(CursosService cursosService) {
        this.cursosService = cursosService;
    }

    @GetMapping
    public ResponseEntity<List<CursosDTO>> listarTodos() {
        return ResponseEntity.ok(cursosService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursosDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursosService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CursosDTO> crear(@Valid @RequestBody CursosDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cursosService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursosDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CursosDTO dto
    ) {
        return ResponseEntity.ok(cursosService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    	cursosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}