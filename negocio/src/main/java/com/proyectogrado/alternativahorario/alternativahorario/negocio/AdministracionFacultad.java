package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Facultad;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaFacultadLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionFacultad implements AdministracionFacultadLocal {

    @EJB
    private FachadaPersistenciaFacultadLocal fachadaPersistenciaFacultad;

    @Override
    public List<Facultad> getFacultades() {
        return fachadaPersistenciaFacultad.findAll();
    }

    @Override
    public Facultad getFacultadPorNombre(String nombre) {
        return fachadaPersistenciaFacultad.findByNombre(nombre);
    }

    @Override
    public boolean eliminarFacultad(Facultad facultad) {
        try {
            Facultad facultadEliminar = fachadaPersistenciaFacultad.findByNombre(facultad.getNombre());
            fachadaPersistenciaFacultad.remove(facultadEliminar);
        } catch (Exception e) {
            System.out.println("Ocurrio un error eliminando la Facultad " + facultad.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public List<Facultad> eliminarFacultades(List<Facultad> facultades) {
        for (Facultad facultad : facultades) {
            eliminarFacultad(facultad);
        }
        return new ArrayList();
    }

    @Override
    public boolean agregarFacultad(Facultad facultad) {
        try {
            fachadaPersistenciaFacultad.create(facultad);
        } catch (Exception e) {
            System.out.println("Ocurrio un error creacion la Facultad " + facultad.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public boolean modificarFacultad(Facultad facultad) {
        try {
            fachadaPersistenciaFacultad.edit(facultad);
        } catch (Exception e) {
            System.out.println("Ocurrio un error modificacion la facultad " + facultad.getNombre());
            return false;
        }
        return true;
    }

}
