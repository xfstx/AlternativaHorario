package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Facultad;
import com.proyectogrado.alternativahorario.entidades.Horario;
import com.proyectogrado.alternativahorario.entidades.Materia;
import com.proyectogrado.alternativahorario.entidades.Menu;
import com.proyectogrado.alternativahorario.entidades.Sede;
import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface FachadaNegocioLocal {

    List<Cromosoma> calcularAlternativa(List<Materia> materiasSeleccionadas);
    
    List<Usuario> getUsuarios();

    boolean eliminarUsuario(Usuario usuario);

    List<Usuario> eliminarUsuarios(List<Usuario> usuarios);

    boolean agregarUsuario(Usuario usuario);

    boolean modificarUsuario(Usuario usuario);

    Usuario getUsuarioPorNombre(String nombre);

    boolean iniciarSesion(Usuario usuario);

    List<Menu> getMenus();

    List<Sede> getSedes();

    boolean eliminarSede(Sede sede);

    List<Sede> eliminarSedes(List<Sede> sedes);

    boolean agregarSede(Sede sede);

    boolean modificarSede(Sede sede);

    Sede getSedePorNombre(String nombre);

    List<Materia> getMaterias();

    boolean eliminarMateria(Materia materia);

    List<Materia> eliminarMaterias(List<Materia> materias);

    boolean agregarMateria(Materia materia);

    boolean modificarMateria(Materia materia);

    Materia getMateriaPorNombre(String nombre);

    List<Materia> getMateriasPorCarreraSemestre(Carrera carrera, int semestre);
    
    List<Carrera> getCarreras();

    boolean eliminarCarrera(Carrera carrera);

    List<Carrera> eliminarCarreras(List<Carrera> carreras);

    boolean agregarCarrera(Carrera carrera);

    boolean modificarCarrera(Carrera carrera);

    Carrera getCarreraPorNombre(String nombre);

    List<Facultad> getFacultades();

    boolean eliminarFacultad(Facultad facultad);

    List<Facultad> eliminarFacultades(List<Facultad> facultades);

    boolean agregarFacultad(Facultad facultad);

    boolean modificarFacultad(Facultad facultad);

    Facultad getFacultadPorNombre(String nombre);

    List<Clase> getClases();

    boolean eliminarClase(Clase clase);

    List<Clase> eliminarClases(List<Clase> clases);

    boolean agregarClase(Clase clase);

    boolean modificarClase(Clase clase);
    
    List<Clase> getClasesPorMateria(Materia materia);

    Clase getClasePorMateriaGrupo(Materia materia, String grupo);
    
    List<Horario> getHorariosPorClase(Clase clase);
            
    boolean eliminarHorario(Horario horario);

    List<Horario> eliminarHorarios(List<Horario> horarios);

    boolean agregarHorario(Horario horario);

}
