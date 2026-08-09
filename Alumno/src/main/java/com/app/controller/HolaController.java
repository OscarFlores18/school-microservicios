package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

    @GetMapping("/holaAlumnos")
    public String decirHola() {
        return "¡Hola Mundo desde el microservicio Alumnos!";
    }
}