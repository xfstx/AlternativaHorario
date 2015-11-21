package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Horario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface AdministracionHorarioLocal {

    List<Horario> getHorarios();
    
    boolean eliminarHorario(Horario horario);

    List<Horario> eliminarHorarios(List<Horario> horarios);

    boolean agregarHorario(Horario horario);

}
