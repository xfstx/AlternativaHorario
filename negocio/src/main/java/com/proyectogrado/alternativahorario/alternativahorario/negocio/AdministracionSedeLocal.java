package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Sede;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface AdministracionSedeLocal {

    List<Sede> getSedes();

    Sede getSedePorNombre(String nombre);

    boolean eliminarSede(Sede sede);
    
    List<Sede> eliminarSedes(List<Sede> sedes);

    boolean agregarSede(Sede sede);

    boolean modificarSede(Sede sede);

}
