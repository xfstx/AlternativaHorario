package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Horario;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaHorarioLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionHorario implements AdministracionHorarioLocal {

    @EJB
    private FachadaPersistenciaHorarioLocal fachadaPersistenciaHorario;

    @Override
    public List<Horario> getHorarios() {
        return fachadaPersistenciaHorario.findAll();
    }

    @Override
    public boolean eliminarHorario(Horario horario) {
        try {
            Horario horarioEliminar = fachadaPersistenciaHorario.find(horario.getId());
            fachadaPersistenciaHorario.remove(horarioEliminar);
        } catch (Exception e) {
            System.out.println("Ocurrio un error eliminando la Horario " + horario.getId());
            return false;
        }
        return true;
    }

    @Override
    public List<Horario> eliminarHorarios(List<Horario> horarios) {
        for (Horario horario : horarios) {
            eliminarHorario(horario);
        }
        return null;
    }

    @Override
    public boolean agregarHorario(Horario horario) {
        try {
            fachadaPersistenciaHorario.create(horario);
        } catch (Exception e) {
            System.out.println("Ocurrio un error creacion la horario " + horario.getId());
            return false;
        }
        return true;
    }

}
