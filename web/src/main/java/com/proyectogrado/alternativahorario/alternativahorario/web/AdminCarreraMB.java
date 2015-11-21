package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Facultad;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Steven
 */
@Named(value = "adminCarrera")
@RequestScoped
public class AdminCarreraMB {
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private List<Carrera> carreras;

    @Getter
    @Setter
    private List<Carrera> carrerasSeleccionadas;

    @Getter
    @Setter
    private List<String> facultades;

    @Getter
    @Setter
    private int cantidadCarreras;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String facultad;

    @Getter
    @Setter
    private String planEstudios;

    @Getter
    @Setter
    private String snies;

    @Getter
    @Setter
    private int creditos;

    @Getter
    @Setter
    private int semestres;

    @Getter
    @Setter
    int materias;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String modNombre;

    @Getter
    @Setter
    private String modFacultad;

    @Getter
    @Setter
    private String modPlanEstudios;

    @Getter
    @Setter
    private String modSnies;

    @Getter
    @Setter
    private int modCreditos;

    @Getter
    @Setter
    private int modSemestres;

    @Getter
    @Setter
    int modMaterias;

    @Getter
    @Setter
    private String modDescripcion;

    @PostConstruct
    public void init() {
        this.facultades = new ArrayList();
        this.carreras = new ArrayList();
        limpiarConsulta();
        llenarFacultades();
    }

    public void llenarFacultades() {
        List<Facultad> facultadesOrig = fachadaNegocio.getFacultades();
        for (Facultad facultad : facultadesOrig) {
            this.facultades.add(facultad.getNombre());
        }
    }

    public void limpiarConsulta() {
        this.carreras = fachadaNegocio.getCarreras();
        this.cantidadCarreras = carreras.size();
    }

    public void limpiarPantalla() {
        this.nombre = "";
        this.facultad = "";
        this.planEstudios = "";
        this.snies = "";
        this.creditos = 0;
        this.semestres = 0;
        this.materias = 0;
        this.descripcion = "";
    }

    public void limpiarModificacion() {
        this.modNombre = "";
        this.modFacultad = "";
        this.modPlanEstudios = "";
        this.modSnies = "";
        this.modCreditos = 0;
        this.modSemestres = 0;
        this.modMaterias = 0;
        this.modDescripcion = "";
    }

    public void agregarCarrera() {
        System.out.println("-< Agregar Carrera");
        if (validarCarrera()) {
            Carrera carrera = new Carrera();
            carrera.setNombre(this.nombre);
            carrera.setFacultad(buscarFacultad(this.facultad));
            carrera.setPlanEstudio(new BigInteger(this.planEstudios));
            carrera.setSnies(this.snies);
            carrera.setCreditos(BigInteger.valueOf(this.creditos));
            carrera.setSemestres(BigInteger.valueOf(this.semestres));
            carrera.setMaterias(BigInteger.valueOf(this.materias));
            carrera.setDescripcion(this.descripcion);

            boolean creacion = fachadaNegocio.agregarCarrera(carrera);
            if (creacion) {
                cerrarCrearDialog();
                limpiarPantalla();
                limpiarConsulta();
                notificarCreacionExitosa();
            } else {
                notificarCreacionFallida();
            }
        }
    }

    public void cerrarCrearDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlAgregarCarrera').hide();");
    }

    public Facultad buscarFacultad(String nombre) {
        return fachadaNegocio.getFacultadPorNombre(nombre);
    }

    public boolean validarCarrera() {
        Carrera carrera = fachadaNegocio.getCarreraPorNombre(nombre);
        if (carrera != null) {
            notificarCarreraYaExiste();
            return false;
        }
        return true;
    }

    public void eliminarCarrera(Carrera carrera) {
        System.out.println("-< Eliminar " + carrera.getNombre() + " - " + carrera.getDescripcion());
        boolean eliminar = fachadaNegocio.eliminarCarrera(carrera);
        if (eliminar) {
            limpiarConsulta();
            notificarEliminacionExitosa();
        } else {
            notificarEliminacionFallida();
        }
    }

    public void eliminarCarreras() {
        if (validarCarreras()) {
            fachadaNegocio.eliminarCarreras(carrerasSeleccionadas);
            limpiarConsulta();
            notificarEliminacionExitosa();
        }
    }

    public boolean validarCarreras() {
        if (carrerasSeleccionadas.isEmpty()) {
            notificarNoSeleccion();
            return false;
        }
        return true;
    }

    public void abrirModificarCarrera(Carrera carrera) {
        System.out.println("abrirModificarCarrera ");
        System.out.println("-< Modificar " + carrera.getNombre() + " - " + carrera.getDescripcion());
        limpiarModificacion();
        this.modNombre = carrera.getNombre();
        this.modFacultad = carrera.getFacultad().getNombre();
        this.modPlanEstudios = carrera.getPlanEstudio() + "";
        this.modSnies = carrera.getSnies();
        this.modCreditos = carrera.getCreditos().intValue();
        this.modSemestres = carrera.getSemestres().intValue();
        this.modMaterias = carrera.getMaterias().intValue();
        this.modDescripcion = carrera.getDescripcion();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlAgregarCarrera').show();");
    }

    public void modificarCarrera() {
        limpiarModificacion();
        System.out.println("Modificar Materia");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlModificarCarrera').hide();");
    }

    public void notificarEliminacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino la/s Carrera/s Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Hubo un error eliminando la Carrera");
        Messages.addFlashGlobal(msg);
    }

    public void notificarCarreraYaExiste() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "Ya existe una Carrera con este Nombre");
        Messages.addFlashGlobal(msg);
    }

    public void notificarCreacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Carrera Creada Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarCreacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error en la creacion de la Carrera");
        Messages.addFlashGlobal(msg);
    }

    public void notificarNoSeleccion() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "No ha seleccionado carreras a Eliminar");
        Messages.addFlashGlobal(msg);
    }

}
