package com.app.Alumno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("com.app.model")
public class AlumnoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlumnoApplication.class, args);
	}

}
