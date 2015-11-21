package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Materia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface FachadaPersistenciaMateriaLocal {
    
    void create(Materia materia);

    void edit(Materia materia);

    void remove(Materia materia);

    Materia find(Object id);

    List<Materia> findAll();

    List<Materia> findRange(int[] range);

    int count();
    
    Materia findByNombre(String nombre);
    
}
