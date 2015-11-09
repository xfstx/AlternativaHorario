package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaCarreraLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionCarrera implements AdministracionCarreraLocal {

    @EJB
    private FachadaPersistenciaCarreraLocal fachadaPersistenciaCarrera;
    
    @Override
    public List<Carrera> getCarreras() {
        return fachadaPersistenciaCarrera.findAll();
    }

}
