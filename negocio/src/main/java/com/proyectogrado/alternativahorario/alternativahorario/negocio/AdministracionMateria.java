package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Materia;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaMateriaLocal;
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
            Materia materiaEliminar = fachadaPersistenciaMateria.find(materia);
            fachadaPersistenciaMateria.remove(materiaEliminar);
        } catch (Exception e) {
            System.out.println("Ocurrio un error eliminando la materia "+materia.getNombre());
            return false;
        }
        return true;
    }

}
