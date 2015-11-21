
package com.proyectogrado.alternativahorario.persistencia;

import com.proyectogrado.alternativahorario.entidades.Horario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface FachadaPersistenciaHorarioLocal {

    void create(Horario horario);

    void edit(Horario horario);

    void remove(Horario horario);

    Horario find(Object id);

    List<Horario> findAll();

    List<Horario> findRange(int[] range);

    int count();
        
}
