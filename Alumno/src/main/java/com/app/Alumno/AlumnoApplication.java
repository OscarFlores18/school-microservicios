package com.app.Alumno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.app")
public class AlumnoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlumnoApplication.class, args);
	}

}
