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
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
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
    private boolean esAddClase;

    @Getter
    @Setter
    private boolean esAddHorario;

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
        this.dia = "";
        this.horaFin = null;
        this.horaInicio = null;
        this.esAddClase = false;
        this.esAddHorario = false;
    }

    public void llenarCarreras() {
        List<Carrera> carrerasOrig = fachadaNegocio.getCarreras();
        for (Carrera car : carrerasOrig) {
            this.carreras.add(car.getNombre());
        }
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
        this.claseSeleccionada = null;
        this.horarioSeleccionado = null;
        this.horarios = null;
        limpiarPantalla();
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
            cargarClases();
        } else {
            notificarAgregarClaseFallida();
        }
        this.esAddClase = false;
    }

    public void eliminarClase() {
        if (validarEliminarClase()) {
            boolean eliminar = fachadaNegocio.eliminarClase(claseSeleccionada);
            if (eliminar) {
                notificarEliminacionClaseExitosa();
            } else {
                notificarEliminacionClaseFallida();
            }
        }
    }

    public boolean validarEliminarClase() {
        if (claseSeleccionada.getHorarioList().size() > 0) {
            notificarClaseAsignada();
            return false;
        }
        return true;
    }

    public void cargarHorarios() {
        limpiarPantalla();
        this.horarios = fachadaNegocio.getHorariosPorClase(claseSeleccionada);
    }

    public void agregarHorario() {
        if (validarHorarioAgregado()) {
            Horario nuevoHorario = new Horario();
            nuevoHorario.setDia(this.dia);
            nuevoHorario.setHorainicio(this.horaInicio);
            nuevoHorario.setHorafin(this.horaFin);
            nuevoHorario.setClases(this.claseSeleccionada);
            this.claseSeleccionada.getHorarioList().add(nuevoHorario);
            boolean modificar = fachadaNegocio.modificarClase(claseSeleccionada);
            if (modificar) {
                notificarAgregarHorarioExitosa();
                limpiarPantalla();
                cargarHorarios();
            } else {
                notificarAgregarHorarioFallida();
            }
            this.esAddHorario = false;
        }
    }

    public boolean validarHorarioAgregado() {
        if (horaInicio.compareTo(horaFin) < 0) {
            if (dia.equals("Seleccione")) {
                notificarSeleccioneDia();
                return false;
            }
            return true;
        } else {
            notificarErrorHoras();
            return false;
        }
    }

    public void eliminarHorarios() {
        boolean eliminar = fachadaNegocio.eliminarHorario(horarioSeleccionado);
        if (eliminar) {
            notificarEliminacionHorarioExitosa();
        } else {
            notificarEliminacionHorarioFallida();
        }
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
        notificarCargaArchivoExitosa();
    }

    public void cargarArchivo(InputStream fis) {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String linea;
        try {
            while ((linea = br.readLine()) != null) {
                try {
                    cargarClase(linea);
                } catch (Exception e) {
                    System.out.println("Error leyendo linea " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Error leyendo linea mientras " + e);
        }
    }

    public void cargarClase(String linea) {
        try {
            String mate = linea.substring(0, linea.indexOf("-"));
            String grupo = linea.substring(linea.indexOf("-") + 1, linea.indexOf("+"));
            String profe = linea.substring(linea.indexOf("+") + 1, linea.indexOf("*"));
            String dia = linea.substring(linea.indexOf("*") + 1, linea.indexOf("/"));
            BigDecimal horaIn = new BigDecimal(linea.substring(linea.indexOf("/") + 1, linea.indexOf("_")));
            BigDecimal horaFi = new BigDecimal(linea.substring(linea.indexOf("_") + 1));

            Materia materia = fachadaNegocio.getMateriaPorNombre(mate);
            if (Objects.nonNull(materia)) {
                Clase clase = fachadaNegocio.getClasePorMateriaGrupo(materia, grupo);
                if (Objects.nonNull(clase)) {
                    crearHorario(clase, dia, horaIn, horaFi);
                } else {
                    crearClase(materia, grupo, profe);
                    clase = fachadaNegocio.getClasePorMateriaGrupo(materia, grupo);
                    crearHorario(clase, dia, horaIn, horaFi);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al insertar clase");
        }
    }

    public void crearClase(Materia materia, String grupo, String profesor) {
        Clase nuevaClase = new Clase();
        nuevaClase.setMateria(materia);
        nuevaClase.setGrupo(grupo);
        nuevaClase.setProfesor(profesor);
        nuevaClase.setHorarioList(new ArrayList());
        materia.getClaseList().add(nuevaClase);
        fachadaNegocio.modificarMateria(materia);
    }

    public void crearHorario(Clase clase, String dia, BigDecimal horaInicio, BigDecimal horaFinal) {
        Horario nuevoHorario = new Horario();
        nuevoHorario.setDia(dia);
        nuevoHorario.setHorainicio(horaInicio);
        nuevoHorario.setHorafin(horaFinal);
        nuevoHorario.setClases(clase);
        clase.getHorarioList().add(nuevoHorario);
        fachadaNegocio.modificarClase(clase);
    }

    public boolean existeClase(String nombreMateria) {
        Materia materia = fachadaNegocio.getMateriaPorNombre(nombreMateria);
        if (materia.getClaseList().isEmpty()) {
            return false;
        }
        return true;
    }

    public Materia buscarMateria(String nombreMateria) {
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

    public void notificarClaseAsignada() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "La clases tiene horarios asignados");
        Messages.addFlashGlobal(msg);
    }

    public void notificarErrorHoras() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "La Hora Inicio es mayor a la Hora Fin");
        Messages.addFlashGlobal(msg);
    }

    public void notificarSeleccioneDia() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Seleccione un dia");
        Messages.addFlashGlobal(msg);
    }
    
    public void notificarCargaArchivoExitosa(){
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Archivo de Clases Cargado Exitosamente.");
        Messages.addFlashGlobal(msg);       
    }

}
