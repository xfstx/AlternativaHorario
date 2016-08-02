package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Facultad;
import com.proyectogrado.alternativahorario.entidades.Sede;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "adminFacultad")
@ViewScoped
public class AdminFacultadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private List<Facultad> facultades;

    @Getter
    @Setter
    private int cantidadFacultades;

    @Getter
    @Setter
    private List<Facultad> facultadesSeleccionadas;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String sede;

    @Getter
    @Setter
    private Facultad facultadSeleccionada;
    
    @Getter
    @Setter
    private List<String> sedes;
    
    @Getter
    @Setter
    private UploadedFile file;

    @PostConstruct
    public void init() {
        limpiarConsulta();
    }

    public void limpiarConsulta() {
        this.nombre = "";
        this.sede = "";
        this.sedes = llenarListaSedes();
        this.facultades = fachadaNegocio.getFacultades();
        this.cantidadFacultades = facultades.size();
    }

    public List<String> llenarListaSedes() {
        List<Sede> sedes = fachadaNegocio.getSedes();
        List<String> sedesLabel = new ArrayList();
        for (Sede sede : sedes) {
            sedesLabel.add(sede.getNombre());                        
        }
        return sedesLabel;
    }
    
    public void eliminarFacultad(Facultad facultad) {
            boolean eliminar = fachadaNegocio.eliminarFacultad(facultad);
            if (eliminar) {
                limpiarConsulta();
                notificarEliminacionExitosa();
            } else {
                notificarEliminacionFallida();
            }
    }
    
    public void eliminarFacultades() {
        if (validarFacultades()) {
            List<Facultad> elim = fachadaNegocio.eliminarFacultades(facultadesSeleccionadas);
            limpiarConsulta();
            if (elim.isEmpty()) {
                notificarEliminacionExitosa();
            } else {
                notificarEliminacionParcial();
            }
        }
    }

    public boolean validarFacultades() {
        if (facultadesSeleccionadas.isEmpty()) {
            notificarNoSeleccion();
            return false;
        }
        return true;
    }

    public void limpiarPantalla() {
        this.nombre = "";
        this.sede = "";
    }

    public void agregarFacultad() {
        if (validarFacultad()) {
            Facultad facultad = new Facultad();
            facultad.setNombre(this.nombre);
            facultad.setSede(buscarSede(this.sede));

            boolean creacion = fachadaNegocio.agregarFacultad(facultad);
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
    
    public Sede buscarSede(String nombreSede) {
        return fachadaNegocio.getSedePorNombre(nombreSede);
    }

    public boolean validarFacultad() {
        Facultad facultad = fachadaNegocio.getFacultadPorNombre(nombre);
        if (facultad != null) {
            notificarFacultadYaExiste();
            return false;
        }
        return true;
    }

    public void cerrarCrearDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlAgregarFacultad').hide();");
        limpiarPantalla();
    }

    public void abrirModificarFacultad(Facultad facultad) {
        this.nombre = facultad.getNombre();
        this.sede = facultad.getSede().getNombre();
        this.facultadSeleccionada = facultad;
        RequestContext.getCurrentInstance().execute("PF('pnlModificarFacultad').show();");
    }
    
    public void abrirAgregarFacultad(){
        this.nombre = "";
        this.sede = "";
        this.facultadSeleccionada = null;
        RequestContext.getCurrentInstance().execute("PF('pnlAgregarFacultad').show();");        
    }

    public void modificarfacultad() {
        this.facultadSeleccionada.setNombre(this.nombre);
        this.facultadSeleccionada.setSede(buscarSede(this.sede));
        boolean modificarFacultad = fachadaNegocio.modificarFacultad(facultadSeleccionada);
        if (modificarFacultad) {
            notificarModificacionExitosa();            
            limpiarConsulta();
        } else {
            notificarModificacionFallida();
        }
        limpiarPantalla();
        RequestContext.getCurrentInstance().execute("PF('pnlModificarFacultad').hide();");
    }
    
    public void cerrarModificarDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlModificarFacultad').hide();");
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
                    cargarFacultad(linea);
                } catch (Exception e) {
                    System.out.println("Error leyendo linea " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Error leyendo linea mientras " + e);
        }
        notificarCargaArchivoExitosa();
    }

    public void cargarFacultad(String linea) {
        String nombreFacultad = linea.substring(0, linea.indexOf("-"));
        String sedeFacultad = linea.substring(linea.indexOf("-") + 1);
        Facultad nuevaFacultad = new Facultad();
        nuevaFacultad.setNombre(nombreFacultad);
        nuevaFacultad.setSede(buscarSede(sedeFacultad));
        if (validarFacultadArchivo(nuevaFacultad)) {
            fachadaNegocio.agregarFacultad(nuevaFacultad);
        }
    }

    public boolean validarFacultadArchivo(Facultad facultadNombre){
        if(facultadNombre.getSede() != null){
            if(validarFacultadNombre(facultadNombre)){
                return true;                
            }            
        }    
        return false;
    }
    
    public boolean validarFacultadNombre(Facultad facultadNombre) {
        Facultad facultad = fachadaNegocio.getFacultadPorNombre(facultadNombre.getNombre());
        if (facultad != null) {
            return false;
        }
        return true;
    }

    public void notificarCreacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Facultad Creada Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarFacultadYaExiste() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "Ya existe una Facultad con este Nombre");
        Messages.addFlashGlobal(msg);
    }

    public void notificarCreacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error en la creacion de la Facultad");
        Messages.addFlashGlobal(msg);
    }

    public void notificarNoSeleccion() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "No ha seleccionado Facultades a Eliminar");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino la/s Facultad/s Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionParcial() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino la/s Facultad/s Parcialmente");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarEliminacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error eliminando la Facultad");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarModificacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Se modifica la facultad Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error al modificar la Facultad");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarFacultadConFacultadAsignada(){
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVER", "No se puede eliminar la facultad por tiene Facultades asignadas");
        Messages.addFlashGlobal(msg);        
    }
    
    public void notificarCargaArchivoExitosa(){
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Archivo de Facultades Cargado Exitosamente.");
        Messages.addFlashGlobal(msg);       
    }
    
}
