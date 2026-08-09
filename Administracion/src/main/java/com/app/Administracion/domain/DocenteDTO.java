package com.app.Administracion.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DocenteDTO(
        Long id,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email,

        @NotBlank(message = "La especialidad es obligatoria")
        String especialidad
) {
}
