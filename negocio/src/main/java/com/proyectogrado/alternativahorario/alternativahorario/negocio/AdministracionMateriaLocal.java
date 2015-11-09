package com.proyectogrado.alternativahorario.alternativahorario.negocio;

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

}
