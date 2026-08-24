package com.app.Cursos.mappers;
import org.springframework.stereotype.Component;
import com.app.Cursos.domain.Cursos;
import com.app.Cursos.domain.CursosDTO;
@Component
public class CursosMappers {
	
   public CursosDTO toDTO(Cursos curso) {
       if (curso == null) {
           return null;
       }
       return new CursosDTO(
               curso.getId(),
               curso.getNombre(),
               curso.getDescripcion(),
               curso.getCurso(),
               curso.getHorario(),
               curso.getId_docente()
       );
   }
  
   public static Cursos toEntity(CursosDTO dto) {
       if (dto == null) {
           return null;
       }
       return new Cursos(
               dto.id(),
               dto.nombre(),
               dto.descripcion(),
               dto.curso(),
               dto.horario(),
               dto.id_docente()
       );
   }
  
   public void updateEntity(Cursos curso, CursosDTO dto) {
   	curso.setNombre(dto.nombre());
   	curso.setDescripcion(dto.descripcion());
   	curso.setCurso(dto.curso());
       curso.setHorario(dto.horario());
       curso.setId_docente(dto.id_docente());
   }
}