package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface AdministracionMateriaLocal {

    List<Materia> getMaterias();
    
    boolean eliminarMateria(Materia materia);
    
    List<Materia> eliminarMaterias(List<Materia> materias);
    
    boolean agregarMateria(Materia materia);
    
    boolean modificarMateria(Materia materia);
    
    Materia getMateriaPorNombre(String nombre);
    
    List<Materia> getMateriasPorCarreraSemestre(Carrera carrera, int semestre);

}
