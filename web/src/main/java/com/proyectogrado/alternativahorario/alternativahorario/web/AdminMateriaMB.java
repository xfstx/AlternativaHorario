package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Materia;
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
@Named(value = "adminMateria")
@ViewScoped
public class AdminMateriaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private List<Materia> materias;

    @Getter
    @Setter
    private int cantidadMaterias;

    @Getter
    @Setter
    private List<Materia> materiasSeleccionadas;

    @Getter
    @Setter
    private List<String> carreras;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private int semestres;

    @Getter
    @Setter
    private String carrera;

    @Getter
    @Setter
    private int creditos;

    @Getter
    @Setter
    private int intesidadHoraria;

    @Getter
    @Setter
    private Materia materiaMod;
    
    @Getter
    @Setter
    private String errorLineas;

    public AdminMateriaMB() {
    }

    @PostConstruct
    public void init() {
        this.carreras = new ArrayList();
        limpiarPantalla();
        llenarCarreras();
    }

    public void limpiarPantalla() {
        this.materias = fachadaNegocio.getMaterias();
        this.cantidadMaterias = materias.size();
        this.errorLineas = "";
    }

    public void limpiarAgregar() {
        this.nombre = "";
        this.carrera = "";
        this.semestres = 0;
        this.creditos = 0;
        this.intesidadHoraria = 0;
    }

    public void llenarCarreras() {
        List<Carrera> listaCarreras = fachadaNegocio.getCarreras();
        for (Carrera carrera : listaCarreras) {
            carreras.add(carrera.getNombre());
        }
    }

    public void consultarMateria() {
        for (Materia materia : materias) {
            System.out.println("-> " + materia.getNombre());
        }
    }

    public void eliminarMateria(Materia materia) {
        boolean eliminar = fachadaNegocio.eliminarMateria(materia);
        if (eliminar) {
            limpiarPantalla();
            notificarEliminacionExitosa();
        } else {
            notificarEliminacionFallida();
        }
    }

    public void eliminarMaterias() {
        if (validarMaterias()) {
            materias = fachadaNegocio.eliminarMaterias(materiasSeleccionadas);
        }
        limpiarPantalla();
        notificarEliminacionExitosa();
    }

    public boolean validarMaterias() {
        if (materiasSeleccionadas.isEmpty()) {
            notificarNoSeleccion();
            return false;
        }
        return true;
    }

    public Carrera buscarCarrera(String carrera) {
        return fachadaNegocio.getCarreraPorNombre(carrera);
    }

    public void agregarMateria() {
        if (validarMateria()) {
            Materia materia = new Materia();
            materia.setNombre(nombre);
            materia.setCarrera(buscarCarrera(carrera));
            materia.setSemestre(this.semestres);
            materia.setCreditos(this.creditos);
            materia.setIntensidadHoraria(this.intesidadHoraria);
            boolean creacion = fachadaNegocio.agregarMateria(materia);
            if (creacion) {
                cerrarCrearDialog();
                limpiarPantalla();
                limpiarAgregar();
                notificarCreacionExitosa();
            } else {
                notificarCreacionFallida();
            }
        }
    }

    public void cerrarCrearDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlAgregarMateria').hide();");
    }

    public boolean validarMateria() {
        Materia materia = fachadaNegocio.getMateriaPorNombre(nombre);
        if (materia != null) {
            notificarMateriaYaExiste();
            return false;
        }
        return true;
    }

    public void abrirModificarMateria(Materia materia) {
        this.materiaMod = materia;
        this.nombre = materia.getNombre();
        determinarCarrera(materia);
        this.semestres = materia.getSemestre();
        this.creditos = materia.getCreditos();
        this.intesidadHoraria = materia.getIntensidadHoraria();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlModificarMateria').show();");
    }

    public void modificarMateria() {
        boolean modifica;
        if (validarMateriaMod()) {
            modifica = fachadaNegocio.modificarMateria(this.materiaMod);
            if (modifica) {
                notificarModificacionExitosa();
            } else {
                notificarModificacionFallida();
            }
        }
    }

    public boolean validarMateriaMod() {
        if (materiaMod.getCarrera().getNombre().equals(this.carrera)
                && materiaMod.getSemestre() == this.semestres
                && materiaMod.getCreditos() == this.creditos
                && materiaMod.getIntensidadHoraria() == this.intesidadHoraria) {
            notificarNoCambio();
            return false;
        } else {
            materiaMod.setCarrera(buscarCarrera(this.carrera));
            materiaMod.setSemestre(this.semestres);
            materiaMod.setCreditos(this.creditos);
            materiaMod.setIntensidadHoraria(this.intesidadHoraria);
            return true;
        }
    }

    public void determinarCarrera(Materia materia) {
        for (String carrera : carreras) {
            if (carrera.equals(materia.getCarrera().getNombre())) {
                this.carrera = carrera;
            }
        }
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
        int numeroLinea = 0;
        try {
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                try {
                    cargarMateria(linea, numeroLinea);
                } catch (Exception e) {
                    System.out.println("Error leyendo linea "+e);
                }
            }
        } catch (Exception e) {
            System.out.println("Error leyendo linea mientras "+e);
        }
        reportarErrorLeyendoArchivo();
    }
        
    public void reportarErrorLeyendoArchivo(){
        if(!errorLineas.isEmpty()){
            notificarErrorLineasArchivo(errorLineas);
        }        
    }

    public void cargarMateria(String linea, int numeroLinea) {
        try {
            System.out.println("" + linea);
            String mate = linea.substring(0, linea.indexOf("-"));
            String carr = linea.substring(linea.indexOf("-") + 1, linea.indexOf("+"));
            int semes = Integer.parseInt(linea.substring(linea.indexOf("+") + 1, linea.indexOf("*")));
            int credi = Integer.parseInt(linea.substring(linea.indexOf("*") + 1, linea.indexOf("/")));
            int inten = Integer.parseInt(linea.substring(linea.indexOf("/") + 1));
            Materia nuevaMateria = new Materia();
            nuevaMateria.setNombre(mate);
            nuevaMateria.setCarrera(buscarCarrera(carr));
            nuevaMateria.setSemestre(semes);
            nuevaMateria.setCreditos(credi);
            nuevaMateria.setIntensidadHoraria(inten);
            boolean creacion = fachadaNegocio.agregarMateria(nuevaMateria);
            if(creacion){
                errorLineas += "Error con la linea "+numeroLinea+" <br/>";
            }
        } catch (Exception e) {
            errorLineas += "Error en la linea "+numeroLinea+" <br/>";
        }        
    }

    public void notificarNoSeleccion() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "No ha seleccionado Materias a Eliminar");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino la/s Materia/s Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarCreacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Materia Creada Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarMateriaYaExiste() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "Ya existe una Materia con este Nombre");
        Messages.addFlashGlobal(msg);
    }

    public void notificarCreacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error en la creacion de la Materia");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error en la eliminacion de la Materia");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Materia Modificada Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarNoCambio() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "No se han hecho cambios en la Materia");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error en la Modificacion de la Materia");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarErrorLineasArchivo(String error) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", error);
        Messages.addFlashGlobal(msg);
        this.errorLineas = "";
    }

}
