package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Materia;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaClaseLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionClase implements AdministracionClaseLocal {

    @EJB
    private FachadaPersistenciaClaseLocal fachadaPersistenciaClase;

    @Override
    public List<Clase> getClases() {
        return fachadaPersistenciaClase.findAll();
    }

    @Override
    public boolean eliminarClase(Clase clase) {
        try {
            Clase claseEliminar = fachadaPersistenciaClase.find(clase.getId());
            fachadaPersistenciaClase.remove(claseEliminar);
        } catch (Exception e) {
            System.out.println("Ocurrio un error eliminando la clase " + clase.getId());
            return false;
        }
        return true;
    }

    @Override
    public List<Clase> eliminarClases(List<Clase> clases) {
        for (Clase clase : clases) {
            eliminarClase(clase);
        }
        return null;
    }

    @Override
    public boolean agregarClase(Clase clase) {
        try {
            fachadaPersistenciaClase.create(clase);
        } catch (Exception e) {
            System.out.println("Ocurrio un error creacion la clase " + clase.getId());
            return false;
        }
        return true;
    }

    @Override
    public List<Clase> getClasesPorMateria(Materia materia) {        
        return fachadaPersistenciaClase.findByMateria(materia);
    }

}
