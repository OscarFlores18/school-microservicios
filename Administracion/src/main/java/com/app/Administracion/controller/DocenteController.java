package com.app.Administracion.controller;

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

import com.app.Administracion.domain.DocenteDTO;
import com.app.Administracion.service.DocenteService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    public ResponseEntity<List<DocenteDTO>> listarTodos() {
        return ResponseEntity.ok(docenteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(docenteService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DocenteDTO> crear(@Valid @RequestBody DocenteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(docenteService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DocenteDTO dto
    ) {
        return ResponseEntity.ok(docenteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        docenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
