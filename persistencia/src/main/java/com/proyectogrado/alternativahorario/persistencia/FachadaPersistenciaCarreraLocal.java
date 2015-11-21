
package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface FachadaPersistenciaCarreraLocal {

    void create(Carrera carrera);

    void edit(Carrera carrera);

    void remove(Carrera carrera);

    Carrera find(Object id);

    List<Carrera> findAll();

    List<Carrera> findRange(int[] range);

    int count();
    
    Carrera findByNombre(String nombre);
    
}
