
package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface FachadaPersistenciaClaseLocal {

    void create(Clase clase);

    void edit(Clase clase);

    void remove(Clase clase);

    Clase find(Object id);

    List<Clase> findAll();

    List<Clase> findRange(int[] range);

    int count();
        
    List<Clase> findByMateria(Materia materia);
    
}
