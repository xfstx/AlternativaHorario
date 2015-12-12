package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Materia;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaMateriaLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionMateria implements AdministracionMateriaLocal {

    @EJB
    private FachadaPersistenciaMateriaLocal fachadaPersistenciaMateria;

    @Override
    public List<Materia> getMaterias() {
        return fachadaPersistenciaMateria.findAll();
    }

    @Override
    public boolean eliminarMateria(Materia materia) {
        try {
            Materia materiaEliminar = fachadaPersistenciaMateria.findByNombre(materia.getNombre());
            fachadaPersistenciaMateria.remove(materiaEliminar);
        } catch (Exception e) {
            System.out.println("Ocurrio un error eliminando la materia " + materia.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public List<Materia> eliminarMaterias(List<Materia> materias) {
        List<Materia> eliminacionParcial = new ArrayList();
        for (Materia materia : materias) {
            eliminarMateria(materia);
        }
        return null;
    }

    @Override
    public boolean agregarMateria(Materia materia) {
        try {
            fachadaPersistenciaMateria.create(materia);
        } catch (Exception e) {
            System.out.println("Ocurrio un error creacion la materia " + materia.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public boolean modificarMateria(Materia materia) {
        try {
            fachadaPersistenciaMateria.edit(materia);
        } catch (Exception e) {
            System.out.println("Ocurrio un error modificacion la materia " + materia.getNombre());
            return false;
        }
        return true;
    }

    @Override
    public Materia getMateriaPorNombre(String nombre) {
        return fachadaPersistenciaMateria.findByNombre(nombre);
    }

    @Override
    public List<Materia> getMateriasPorCarreraSemestre(Carrera carrera, int semestre) {
        return fachadaPersistenciaMateria.findByCarreraSemestre(carrera, semestre);
    }

}
