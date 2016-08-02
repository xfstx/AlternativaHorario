package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Horario;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import org.primefaces.event.FileUploadEvent;
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
    
    @Getter
    @Setter
    private String errorLineas;
    
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
        this.errorLineas = "";
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
                    cargarClase(linea, numeroLinea);
                } catch (Exception e) {
                    System.out.println("Error leyendo linea " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Error leyendo linea mientras " + e);
        }
        reportarErrorLeyendoArchivo();
    }

    public void reportarErrorLeyendoArchivo() {
        if (!errorLineas.isEmpty()) {
            notificarErrorLineasArchivo(errorLineas);
        }
    }

    public void cargarClase(String linea, int numeroLinea) {
        try {
            String mate = linea.substring(0, linea.indexOf("-"));
            String carr = linea.substring(linea.indexOf("-") + 1, linea.indexOf("+"));
            String grupo = linea.substring(linea.indexOf("+") + 1, linea.indexOf("*"));
            String dia = linea.substring(linea.indexOf("*") + 1, linea.indexOf("/"));
            int horaIn = Integer.parseInt(linea.substring(linea.indexOf("/") + 1, linea.indexOf("_")));
            int horaFi = Integer.parseInt(linea.substring(linea.indexOf("_") + 1, linea.indexOf(",")));
            String profe = linea.substring(linea.indexOf(",") + 1);
            
            Materia mater = fachadaNegocio.getMateriaPorNombre(mate);
            Clase clase = fachadaNegocio.getClasePorMateriaGrupo(mater, grupo);
            
            Horario nuevoHorario = new Horario();
            nuevoHorario.setClases(clase);
            nuevoHorario.setDia(dia);
            nuevoHorario.setHorafin(new BigDecimal(horaFi));
            nuevoHorario.setHorainicio(new BigDecimal(horaIn));
            
            if (Objects.isNull(clase)) {
                clase = new Clase();
                clase.setGrupo(grupo);
                clase.setMateria(mater);
                clase.setProfesor(profe);
                List<Horario> hora = new ArrayList();
                hora.add(nuevoHorario);
                clase.setHorarioList(hora);
                boolean creacion = fachadaNegocio.agregarClase(clase);
            } else {
                clase.getHorarioList().add(nuevoHorario);
                boolean modificar = fachadaNegocio.modificarClase(clase);
            }            

        } catch (Exception e) {
            errorLineas += "Error en la linea " + numeroLinea + " <br/>";
        }
    }

    public boolean existeClase(String nombreMateria) {
        Materia materia = fachadaNegocio.getMateriaPorNombre(nombreMateria);
        if (materia.getClaseList().isEmpty()) {
            return false;
        }
        return true;
    }
    
    public Materia buscarMateria(String nombreMateria){
        return fachadaNegocio.getMateriaPorNombre(nombreMateria);
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
    
    public void notificarErrorLineasArchivo(String error) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", error);
        Messages.addFlashGlobal(msg);
        this.errorLineas = "";
    }
    
}
