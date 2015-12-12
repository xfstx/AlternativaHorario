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
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaNegocio implements FachadaNegocioLocal {

    @EJB
    private AdministracionUsuarioLocal administracionUsuario;

    @EJB
    private AdministracionMenuLocal administracionMenu;

    @EJB
    private AdministracionSedeLocal administracionSede;

    @EJB
    private AdministracionMateriaLocal administracionMateria;

    @EJB
    private AdministracionCarreraLocal administracionCarrera;

    @EJB
    private AdministracionFacultadLocal administracionFacultad;

    @EJB
    private AdministracionClaseLocal administracionClase;

    @EJB
    private AdministracionHorarioLocal administracionHorario;

    @Override
    public List<Usuario> getUsuarios() {
        return administracionUsuario.getUsuarios();
    }

    @Override
    public boolean eliminarUsuario(Usuario usuario) {
        return administracionUsuario.eliminarUsuario(usuario);
    }

    @Override
    public List<Usuario> eliminarUsuarios(List<Usuario> usuarios) {
        return administracionUsuario.eliminarUsuarios(usuarios);
    }

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        return administracionUsuario.agregarUsuario(usuario);
    }

    @Override
    public boolean modificarUsuario(Usuario usuario) {
        return administracionUsuario.modificarUsuario(usuario);
    }

    @Override
    public Usuario getUsuarioPorNombre(String nombre) {
        return administracionUsuario.getUsuarioPorNombre(nombre);
    }

    @Override
    public boolean iniciarSesion(Usuario usuario) {
        return administracionUsuario.iniciarSesion(usuario);
    }

    @Override
    public List<Menu> getMenus() {
        return administracionMenu.getMenus();
    }

    @Override
    public List<Sede> getSedes() {
        return administracionSede.getSedes();
    }

    @Override
    public boolean eliminarSede(Sede sede) {
        return administracionSede.eliminarSede(sede);
    }

    @Override
    public List<Sede> eliminarSedes(List<Sede> sedes) {
        return administracionSede.eliminarSedes(sedes);
    }

    @Override
    public boolean agregarSede(Sede sede) {
        return administracionSede.agregarSede(sede);
    }

    @Override
    public boolean modificarSede(Sede sede) {
        return administracionSede.modificarSede(sede);
    }

    @Override
    public Sede getSedePorNombre(String nombre) {
        return administracionSede.getSedePorNombre(nombre);
    }

    @Override
    public List<Materia> getMaterias() {
        return administracionMateria.getMaterias();
    }

    @Override
    public boolean eliminarMateria(Materia materia) {
        return administracionMateria.eliminarMateria(materia);
    }

    @Override
    public List<Materia> eliminarMaterias(List<Materia> materias) {
        return administracionMateria.eliminarMaterias(materias);
    }

    @Override
    public boolean agregarMateria(Materia materia) {
        return administracionMateria.agregarMateria(materia);
    }

    @Override
    public boolean modificarMateria(Materia materia) {
        return administracionMateria.modificarMateria(materia);
    }

    @Override
    public Materia getMateriaPorNombre(String nombre) {
        return administracionMateria.getMateriaPorNombre(nombre);
    }

    @Override
    public List<Materia> getMateriasPorCarreraSemestre(Carrera carrera, int semestre) {
        return administracionMateria.getMateriasPorCarreraSemestre(carrera, semestre);
    }

    @Override
    public List<Carrera> getCarreras() {
        return administracionCarrera.getCarreras();
    }

    @Override
    public boolean eliminarCarrera(Carrera carrera) {
        return administracionCarrera.eliminarCarrera(carrera);
    }

    @Override
    public List<Carrera> eliminarCarreras(List<Carrera> carreras) {
        return administracionCarrera.eliminarCarreras(carreras);
    }

    @Override
    public boolean agregarCarrera(Carrera carrera) {
        return administracionCarrera.agregarCarrera(carrera);
    }

    @Override
    public boolean modificarCarrera(Carrera carrera) {
        return administracionCarrera.modificarCarrera(carrera);
    }

    @Override
    public Carrera getCarreraPorNombre(String nombre) {
        return administracionCarrera.getCarreraPorNombre(nombre);
    }

    @Override
    public List<Facultad> getFacultades() {
        return administracionFacultad.getFacultades();
    }

    @Override
    public boolean eliminarFacultad(Facultad facultad) {
        return administracionFacultad.eliminarFacultad(facultad);
    }

    @Override
    public List<Facultad> eliminarFacultades(List<Facultad> facultades) {
        return administracionFacultad.eliminarFacultades(facultades);
    }

    @Override
    public boolean agregarFacultad(Facultad facultad) {
        return administracionFacultad.agregarFacultad(facultad);
    }

    @Override
    public boolean modificarFacultad(Facultad facultad) {
        return administracionFacultad.modificarFacultad(facultad);
    }

    @Override
    public Facultad getFacultadPorNombre(String nombre) {
        return administracionFacultad.getFacultadPorNombre(nombre);
    }

    @Override
    public List<Clase> getClases() {
        return administracionClase.getClases();
    }

    @Override
    public boolean eliminarClase(Clase clase) {
        return administracionClase.eliminarClase(clase);
    }

    @Override
    public List<Clase> eliminarClases(List<Clase> clases) {
        return administracionClase.eliminarClases(clases);
    }

    @Override
    public boolean agregarClase(Clase clase) {
        return administracionClase.agregarClase(clase);
    }

    @Override
    public List<Clase> getClasesPorMateria(Materia materia) {
        return administracionClase.getClasesPorMateria(materia);
    }

    @Override
    public boolean eliminarHorario(Horario horario) {
        return administracionHorario.eliminarHorario(horario);
    }

    @Override
    public List<Horario> eliminarHorarios(List<Horario> horarios) {
        return administracionHorario.eliminarHorarios(horarios);
    }

    @Override
    public boolean agregarHorario(Horario horario) {
        return administracionHorario.agregarHorario(horario);
    }

}
