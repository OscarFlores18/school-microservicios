package com.app.Cursos.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursosDTO(
		Long id,

		@NotBlank(message = "El nombre es obligatorio")
		String nombre,

		@NotBlank(message = "La descripcion es obligatoria")
		String descripcion,

		@NotBlank(message = "El curso es obligatorio")
		String curso,

		@NotBlank(message = "El horario es obligatorio")
		String horario,

		@NotNull(message = "El docente es obligatorio")
		Long id_docente
) {
}