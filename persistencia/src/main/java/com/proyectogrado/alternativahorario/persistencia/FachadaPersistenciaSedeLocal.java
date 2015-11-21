
package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Sede;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface FachadaPersistenciaSedeLocal {

    void create(Sede sede);

    void edit(Sede sede);

    void remove(Sede sede);

    Sede find(Object id);

    List<Sede> findAll();

    List<Sede> findRange(int[] range);

    int count();
    
    Sede findByNombre(String nombre);
    
}
