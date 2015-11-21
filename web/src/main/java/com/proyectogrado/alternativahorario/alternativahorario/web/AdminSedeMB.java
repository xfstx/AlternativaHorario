package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Sede;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Steven
 */
@Named(value = "adminSede")
@RequestScoped
public class AdminSedeMB {

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private List<Sede> sedes;

    @Getter
    @Setter
    private int cantidadSedes;

    @Getter
    @Setter
    private List<Sede> sedesSeleccionadas;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String direccion;

    @PostConstruct
    public void init() {
        limpiarConsulta();
    }

    public void limpiarConsulta() {
        this.nombre = "";
        this.direccion = "";
        this.sedes = fachadaNegocio.getSedes();
        this.cantidadSedes = sedes.size();
    }

    public void eliminarSede(Sede sede) {
        boolean eliminar = fachadaNegocio.eliminarSede(sede);
        if (eliminar) {
            limpiarConsulta();
            notificarEliminacionExitosa();
        } else {
            notificarEliminacionFallida();
        }
    }

    public void eliminarSedes() {
        if (validarSedes()) {
            fachadaNegocio.eliminarSedes(sedesSeleccionadas);
            limpiarConsulta();
            notificarEliminacionExitosa();
        }
    }

    public boolean validarSedes() {
        if (sedesSeleccionadas.isEmpty()) {
            notificarNoSeleccion();
            return false;
        }
        return true;
    }

    public void limpiarPantalla() {
        this.nombre = "";
        this.direccion = "";
    }

    public void agregarSede() {
        if (validarSede()) {
            Sede sede = new Sede();
            sede.setNombre(this.nombre);
            sede.setDireccion(this.direccion);

            boolean creacion = fachadaNegocio.agregarSede(sede);
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

    public boolean validarSede() {
        Sede sede = fachadaNegocio.getSedePorNombre(nombre);
        if (sede != null) {
            notificarSedeYaExiste();
            return false;
        }
        return true;
    }

    public void cerrarCrearDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlAgregarSede').hide();");
    }

    public void abrirModificarSede(Sede sede) {
        this.nombre = sede.getNombre();
        this.direccion = sede.getDireccion();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlModificarSede').show();");
    }

    public void modificarSede(Sede sede) {
        limpiarPantalla();
        this.nombre = sede.getNombre();
        this.direccion = sede.getDireccion(); // TODO
    }

    public void cerrarModificarDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlModificarSede').hide();");
    }

    public void notificarCreacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Sede Creada Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarSedeYaExiste() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "Ya existe una Sede con este Nombre");
        Messages.addFlashGlobal(msg);
    }

    public void notificarCreacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error en la creacion de la Sede");
        Messages.addFlashGlobal(msg);
    }

    public void notificarNoSeleccion() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "No ha seleccionado Sedes a Eliminar");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino la/s Sede/s Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Hubo un error eliminando la Sede");
        Messages.addFlashGlobal(msg);
    }

}
