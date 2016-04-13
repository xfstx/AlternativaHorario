package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Horario;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Steven
 */
@Named(value = "adminClase")
@ViewScoped
public class AdminClaseMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private String grupo;

    @Getter
    @Setter
    private List<String> dias;

    @Getter
    @Setter
    private String profesor;
    
    @Getter
    @Setter
    private String dia;

    @Getter
    @Setter
    private BigDecimal horaInicio;

    @Getter
    @Setter
    private BigDecimal horaFin;

    @Getter
    @Setter
    private List<String> carreras;

    @Getter
    @Setter
    private String carrera;

    @Getter
    @Setter
    private int semestre;

    @Getter
    @Setter
    private UploadedFile file;

    @Getter
    @Setter
    private List<Materia> materias;

    @Getter
    @Setter
    private Materia materiaSeleccionada;

    @Getter
    @Setter
    private List<Clase> clases;

    @Getter
    @Setter
    private Clase claseSeleccionada;

    @Getter
    @Setter
    private List<Horario> horarios;

    @Getter
    @Setter
    private Horario horarioSeleccionado;

    @Getter
    @Setter
    private int cantidadMaterias;
    
    @PostConstruct
    public void init() {
        this.materias = new ArrayList();
        this.clases = new ArrayList();
        this.dias = new ArrayList();
        this.horarios = new ArrayList();
        this.carreras = new ArrayList();
        llenarDias();
        limpiarPantalla();
        llenarCarreras();
    }

    public void limpiarPantalla() {
        this.profesor = "";
        this.grupo = "";
        this.materias = fachadaNegocio.getMaterias();
        this.cantidadMaterias = materias.size();
    }

    public void llenarCarreras() {
        List<Carrera> carrerasOrig = fachadaNegocio.getCarreras();
        for (Carrera car : carrerasOrig) {
            this.carreras.add(car.getNombre());
        }
    }

    public void prueba(){
        System.out.println("This is a test");
    }
    
    public Carrera buscarCarrera(String nombreCarrera) {
        return fachadaNegocio.getCarreraPorNombre(nombreCarrera);
    }

    public void llenarDias() {
        this.dias.add("Lunes");
        this.dias.add("Martes");
        this.dias.add("Miercoles");
        this.dias.add("Jueves");
        this.dias.add("Viernes");
        this.dias.add("Sabado");
    }

    public void cargarClases() {
        this.clases = fachadaNegocio.getClasesPorMateria(materiaSeleccionada);
    }
    
    public void agregarClase() {
        Clase nuevaClase = new Clase();
        nuevaClase.setMateria(this.materiaSeleccionada);
        nuevaClase.setGrupo(this.grupo);
        nuevaClase.setProfesor(this.profesor);
        nuevaClase.setHorarioList(new ArrayList());
        this.materiaSeleccionada.getClaseList().add(nuevaClase);
        boolean modificar = fachadaNegocio.modificarMateria(this.materiaSeleccionada);
        if (modificar) {
            notificarAgregarClaseExitosa();
            limpiarPantalla();            
        } else {
            notificarAgregarClaseFallida();
        }
        cerrarCrearDialogAgregarClase();
    }

    public void cerrarCrearDialogAgregarClase() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlAddClass').hide();");
    }
    
    public void eliminarClase() {
        boolean eliminar = fachadaNegocio.eliminarClase(claseSeleccionada);
        if (eliminar) {
            notificarEliminacionClaseExitosa();
        } else {
            notificarEliminacionClaseFallida();
        }
    }
    
    public void cargarHorarios() {
        this.horarios = fachadaNegocio.getHorariosPorClase(claseSeleccionada);
    }
    
    public void agregarHorario() {
        Horario nuevoHorario = new Horario();
        nuevoHorario.setDia(this.dia);
        nuevoHorario.setHorainicio(this.horaInicio);
        nuevoHorario.setHorafin(this.horaFin);
        nuevoHorario.setClases(this.claseSeleccionada);
        this.claseSeleccionada.getHorarioList().add(nuevoHorario);
        boolean modificar = fachadaNegocio.modificarClase(claseSeleccionada);
        if (modificar) {
            notificarAgregarHorarioExitosa();
        } else {
            notificarAgregarHorarioFallida();
        }
        cerrarCrearDialogAgregarHorario();
    }

    public void eliminarHorarios() {
        boolean eliminar = fachadaNegocio.eliminarHorario(horarioSeleccionado);
        if (eliminar) {
            notificarEliminacionHorarioExitosa();
        } else {
            notificarEliminacionHorarioFallida();
        }
    }

    public void cerrarCrearDialogAgregarHorario() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlAgregarHorario').hide();");
    }

    public String getFechaFormateada(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        return sdf.format(fecha);
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Exitoso", file.getFileName() + " a sido cargado");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void notificarEliminacionClaseExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino la Clase Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionClaseFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Hubo un error eliminando la Clase");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarEliminacionHorarioExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino el Horario Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionHorarioFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Hubo un error eliminando el Horario");
        Messages.addFlashGlobal(msg);
    }

    public void notificarAgregarClaseExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se agrego la clase Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarAgregarClaseFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Hubo un error agregando la clase");
        Messages.addFlashGlobal(msg);
    }

    public void notificarAgregarHorarioExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se agrego el horario Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarAgregarHorarioFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Hubo un error agregando el horario");
        Messages.addFlashGlobal(msg);
    }
 
}
