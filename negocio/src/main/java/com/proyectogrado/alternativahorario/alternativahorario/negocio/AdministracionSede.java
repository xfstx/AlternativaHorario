package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Sede;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaSedeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionSede implements AdministracionSedeLocal {

    @EJB
    private FachadaPersistenciaSedeLocal fachadaPersistenciaSede;

    @Override
    public List<Sede> getSedes() {
        return fachadaPersistenciaSede.findAll();
    }

    @Override
    public Sede getSedePorNombre(String nombre) {
        return fachadaPersistenciaSede.findByNombre(nombre);
    }

    @Override
    public boolean eliminarSede(Sede sede) {
        try {
            Sede sedeEliminar = fachadaPersistenciaSede.findByNombre(sede.getNombre());
            fachadaPersistenciaSede.remove(sedeEliminar);
        } catch (Exception e) {
            System.out.println("Ocurrio un error eliminando la sede " + sede.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public List<Sede> eliminarSedes(List<Sede> sedes) {
        List<Sede> sedesNoEliminadas = new ArrayList();
        for (Sede sede : sedes) {
            if (sede.getFacultadList().size() > 0) {
                sedesNoEliminadas.add(sede);
            } else {
                eliminarSede(sede);
            }
        }
        return sedesNoEliminadas;
    }

    @Override
    public boolean agregarSede(Sede sede) {
        try {
            fachadaPersistenciaSede.create(sede);
        } catch (Exception e) {
            System.out.println("Ocurrio un error creacion la sede " + sede.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public boolean modificarSede(Sede sede) {
        try {
            fachadaPersistenciaSede.edit(sede);
        } catch (Exception e) {
            System.out.println("Ocurrio un error modificacion la sede " + sede.getNombre());
            return false;
        }
        return true;
    }

}
