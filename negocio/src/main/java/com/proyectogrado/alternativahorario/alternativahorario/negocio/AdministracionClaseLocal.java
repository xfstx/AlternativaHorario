package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface AdministracionClaseLocal {

    List<Clase> getClases();

    boolean eliminarClase(Clase clase);

    List<Clase> eliminarClases(List<Clase> clases);

    boolean agregarClase(Clase clase);

    List<Clase> getClasesPorMateria(Materia materia);

}
