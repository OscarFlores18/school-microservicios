package com.app.Cursos.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CursoDTO(
        Long id,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        String descripcion,

        @NotNull(message = "El año es obligatorio")
        @Min(value = 2000, message = "El año no es válido")
        Integer anio,

        @NotBlank(message = "El turno es obligatorio")
        String turno,

        @NotNull(message = "El cupo máximo es obligatorio")
        @Positive(message = "El cupo máximo debe ser mayor a cero")
        Integer cupoMaximo
) {
}