package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Sede;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Steven
 */
@Named(value = "adminSede")
@ViewScoped
public class AdminSedeMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
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

    @Getter
    @Setter
    private Sede sedeSeleccionada;
    
    @Getter
    @Setter
    private UploadedFile file;

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
        if (validarSede(sede)) {
            boolean eliminar = fachadaNegocio.eliminarSede(sede);
            if (eliminar) {
                limpiarConsulta();
                notificarEliminacionExitosa();
            } else {
                notificarEliminacionFallida();
            }
        }
    }

    public boolean validarSede(Sede sede){
        if(sede.getFacultadList().size() > 0){
            notificarSedeConFacultadAsignada();
            return false;
        }
        return true;        
    }
    
    public void eliminarSedes() {
        if (validarSedes()) {
            List<Sede> elim = fachadaNegocio.eliminarSedes(sedesSeleccionadas);
            limpiarConsulta();
            if (elim.isEmpty()) {
                notificarEliminacionExitosa();
            } else {
                notificarEliminacionParcial();
            }
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
        limpiarPantalla();
    }

    public void abrirModificarSede(Sede sede) {
        this.nombre = sede.getNombre();
        this.direccion = sede.getDireccion();
        this.sedeSeleccionada = sede;
        RequestContext.getCurrentInstance().execute("PF('pnlModificarSede').show();");
    }

    public void modificarSede() {
        this.sedeSeleccionada.setNombre(this.nombre);
        this.sedeSeleccionada.setDireccion(this.direccion);
        boolean modificarSede = fachadaNegocio.modificarSede(sedeSeleccionada);
        if (modificarSede) {
            notificarModificacionExitosa();            
            limpiarConsulta();
        } else {
            notificarModificacionFallida();
        }
        limpiarPantalla();
        RequestContext.getCurrentInstance().execute("PF('pnlModificarSede').hide();");
    }

    public boolean validarSedeNombre(Sede sedeNombre) {
        Sede sede = fachadaNegocio.getSedePorNombre(sedeNombre.getNombre());
        if (sede != null) {
            return false;
        }
        return true;
    }
    
    public void cerrarModificarDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlModificarSede').hide();");
        limpiarPantalla();
    }

    public void upload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        InputStream archivo;
        try {
            archivo = file.getInputstream();
            cargarArchivo(archivo);
        } catch (Exception e) {
            System.out.println("Error leyendo archivo " + e);
        }
    }
    
    public void cargarArchivo(InputStream fis) {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String linea;
        try {
            while ((linea = br.readLine()) != null) {
                try {
                    cargarSede(linea);
                } catch (Exception e) {
                    System.out.println("Error leyendo linea " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Error leyendo linea mientras " + e);
        }        
        notificarCargaArchivoExitosa();
    }
    
    public void cargarSede(String linea) {
        String nombreSede = linea.substring(0, linea.indexOf("-"));
        String direccionSede = linea.substring(linea.indexOf("-") + 1);
        Sede nuevaSede = new Sede();
        nuevaSede.setNombre(nombreSede);
        nuevaSede.setDireccion(direccionSede);
        if (validarSedeNombre(nuevaSede)) {
            fachadaNegocio.agregarSede(nuevaSede);
        }
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

    public void notificarEliminacionParcial() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino la/s Sede/s Parcialmente");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarEliminacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error eliminando la Sede");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarModificacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Se modifica la sede Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error al modificar la Sede");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarSedeConFacultadAsignada(){
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVER", "No se puede eliminar la sede por tiene Facultades asignadas");
        Messages.addFlashGlobal(msg);        
    }
    
    public void notificarCargaArchivoExitosa(){
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Archivo de Sedes Cargado Exitosamente.");
        Messages.addFlashGlobal(msg);       
    }
    
}
