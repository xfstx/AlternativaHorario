package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Sede;
import java.io.Serializable;
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
@Named(value = "modificacionSede")
@ViewScoped
public class ModificacionSedeMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;
    
    @Getter
    @Setter
    private String id;
    
    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String direccion;

    @Getter
    @Setter
    private Sede sedeSeleccionada;
    
    @PostConstruct
    public void init() {
    }
    
    public void cargarDatos() {
        sedeSeleccionada = fachadaNegocio.getSedePorNombre(nombre);
        direccion = sedeSeleccionada.getDireccion();
    }

    public void modificarSede() {
        this.sedeSeleccionada.setNombre(this.nombre);
        this.sedeSeleccionada.setDireccion(this.direccion);
        boolean modificarSede = fachadaNegocio.modificarSede(sedeSeleccionada);
        if (modificarSede) {
            notificarModificacionExitosa();
        } else {
            notificarModificacionFallida();
        }
    }

    public void notificarModificacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Se modifica la sede Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error al modificar la Sede");
        Messages.addFlashGlobal(msg);
    }
    
}
