package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface AdministracionCarreraLocal {

    List<Carrera> getCarreras();

    boolean eliminarCarrera(Carrera carrera);

    List<Carrera> eliminarCarreras(List<Carrera> carreras);

    boolean agregarCarrera(Carrera carrera);

    boolean modificarCarrera(Carrera carrera);

    Carrera getCarreraPorNombre(String nombre);

}
