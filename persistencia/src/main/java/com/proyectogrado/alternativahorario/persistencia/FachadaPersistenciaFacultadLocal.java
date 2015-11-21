
package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Facultad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface FachadaPersistenciaFacultadLocal {

    void create(Facultad facultad);

    void edit(Facultad facultad);

    void remove(Facultad facultad);

    Facultad find(Object id);

    List<Facultad> findAll();

    List<Facultad> findRange(int[] range);

    int count();
    
    Facultad findByNombre(String nombre);
    
}
