package com.app.Cursos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cursos")
public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String curso;
    private String horario;
    private Long id_docente;

    public Cursos() {
    }

	public Cursos(Long id, String nombre, String descripcion, String curso, String horario, Long id_docente) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.curso = curso;
		this.horario = horario;
		this.id_docente = id_docente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Long getId_docente() {
		return id_docente;
	}

	public void setId_docente(Long id_docente) {
		this.id_docente = id_docente;
	}

   
}