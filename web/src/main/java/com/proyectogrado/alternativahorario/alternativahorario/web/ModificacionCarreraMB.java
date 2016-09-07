package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Facultad;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

/**
 *
 * @author Steven
 */
@Named(value = "modificacionCarrera")
@ViewScoped
public class ModificacionCarreraMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private Carrera carreraSeleccionada;
    
    @Getter
    @Setter
    private List<String> facultades;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String facultad;

    @Getter
    @Setter
    private BigInteger planEstudios;

    @Getter
    @Setter
    private String snies;

    @Getter
    @Setter
    private BigInteger creditos;

    @Getter
    @Setter
    private BigInteger semestres;

    @Getter
    @Setter
    private BigInteger materias;

    @Getter
    @Setter
    private String descripcion;
    
    @PostConstruct
    public void init() {
        this.facultades = new ArrayList();
        llenarFacultades();        
    }
    
    public void cargarDatos() {
        carreraSeleccionada = fachadaNegocio.getCarreraPorNombre(nombre);
        facultad = carreraSeleccionada.getFacultad().getNombre(); 
        planEstudios = carreraSeleccionada.getPlanEstudio();
        snies = carreraSeleccionada.getSnies();
        creditos = carreraSeleccionada.getCreditos();
        semestres = carreraSeleccionada.getSemestres();
        materias = carreraSeleccionada.getMaterias();
        descripcion = carreraSeleccionada.getDescripcion();        
    }

    public Facultad buscarFacultad(String nombre) {
        return fachadaNegocio.getFacultadPorNombre(nombre);
    }
    
    public void llenarFacultades() {
        List<Facultad> facultadesOrig = fachadaNegocio.getFacultades();
        for (Facultad facultad : facultadesOrig) {
            this.facultades.add(facultad.getNombre());
        }
    }
    
    public void modificarCarrera() {
        carreraSeleccionada.setFacultad(buscarFacultad(facultad));
        carreraSeleccionada.setPlanEstudio(planEstudios);
        carreraSeleccionada.setSnies(snies);
        carreraSeleccionada.setCreditos(creditos);
        carreraSeleccionada.setSemestres(semestres);
        carreraSeleccionada.setMaterias(materias);
        carreraSeleccionada.setDescripcion(descripcion);
        if (fachadaNegocio.modificarCarrera(carreraSeleccionada)) {
            notificarModificacionExitosa();
        } else {
            notificarModificacionFallida();
        }
    }

    public void notificarModificacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Se modifica la Carrera Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error al modificar la Carrera");
        Messages.addFlashGlobal(msg);
    }

}
