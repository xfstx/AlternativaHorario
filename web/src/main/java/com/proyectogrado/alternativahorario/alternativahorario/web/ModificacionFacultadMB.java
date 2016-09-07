package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Facultad;
import com.proyectogrado.alternativahorario.entidades.Sede;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Setter;
import lombok.Getter;
import org.omnifaces.util.Messages;

/**
 *
 * @author Steven
 */
@Named(value = "modificacionFacultad")
@ViewScoped
public class ModificacionFacultadMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private List<String> sedes;
    
    @Getter
    @Setter
    private Facultad facultadSeleccionada;
    
    @Getter
    @Setter
    private String nombre;
            
    @Getter
    @Setter
    private String sede;
    
    @PostConstruct
    public void init() {
        sedes = llenarListaSedes();
    }

    public void cargarDatos() {
        facultadSeleccionada = fachadaNegocio.getFacultadPorNombre(nombre);
        sede = facultadSeleccionada.getSede().getNombre();
    }

    public List<String> llenarListaSedes() {
        List<Sede> sedes = fachadaNegocio.getSedes();
        List<String> sedesLabel = new ArrayList();
        for (Sede sede : sedes) {
            sedesLabel.add(sede.getNombre());
        }
        return sedesLabel;
    }
    
    public Sede buscarSede(String nombreSede) {
        return fachadaNegocio.getSedePorNombre(nombreSede);
    }
    
    public void modificarfacultad() {
        facultadSeleccionada.setNombre(this.nombre);
        facultadSeleccionada.setSede(buscarSede(this.sede));
        boolean modificarFacultad = fachadaNegocio.modificarFacultad(facultadSeleccionada);
        if (modificarFacultad) {
            notificarModificacionExitosa();
        } else {
            notificarModificacionFallida();
        }
    }
    
    public void notificarModificacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Se modifica la facultad Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error al modificar la Facultad");
        Messages.addFlashGlobal(msg);
    }
    
}
