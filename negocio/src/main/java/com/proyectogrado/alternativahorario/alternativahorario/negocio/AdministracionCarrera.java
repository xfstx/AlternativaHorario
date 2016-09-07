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

    @Override
    public boolean eliminarCarrera(Carrera carrera) {
        try {
            Carrera carreraEliminar = fachadaPersistenciaCarrera.findByNombre(carrera.getNombre());
            fachadaPersistenciaCarrera.remove(carreraEliminar);
        } catch (Exception e) {
            System.out.println("Ocurrio un error eliminando la carrera " + carrera.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public List<Carrera> eliminarCarreras(List<Carrera> carreras) {
        for (Carrera carrera : carreras) {
            eliminarCarrera(carrera);
        }
        return null;
    }

    @Override
    public boolean agregarCarrera(Carrera carrera) {
        try {
            fachadaPersistenciaCarrera.create(carrera);
        } catch (Exception e) {
            System.out.println("Ocurrio un error creacion la carrera " + carrera.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public boolean modificarCarrera(Carrera carrera) {
        try {
            fachadaPersistenciaCarrera.edit(carrera);
        } catch (Exception e) {
            System.out.println("Ocurrio un error modificacion la carrera " + carrera.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public Carrera getCarreraPorNombre(String nombre) {
        return fachadaPersistenciaCarrera.findByNombre(nombre);
    }
}
