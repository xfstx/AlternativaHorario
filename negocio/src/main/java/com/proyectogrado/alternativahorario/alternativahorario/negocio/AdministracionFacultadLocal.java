package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Facultad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface AdministracionFacultadLocal {

    List<Facultad> getFacultades();

    boolean eliminarFacultad(Facultad facultad);

    List<Facultad> eliminarFacultades(List<Facultad> facultades);

    boolean agregarFacultad(Facultad facultad);

    boolean modificarFacultad(Facultad facultad);

    Facultad getFacultadPorNombre(String nombre);

}
