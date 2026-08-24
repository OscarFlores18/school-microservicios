package com.app.Cursos.domain;

import jakarta.validation.constraints.NotBlank;

public record CursosDTO(
		Long id,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "La descripcion es obligatorio")
        String descripcion,

        @NotBlank(message = "El curso es obligatorio")
		String curso,
		
        @NotBlank(message = "El horario es obligatorio")
        String horario,

        @NotBlank(message = "El docente es obligatoria")
        Long id_docente
		
		){
}

