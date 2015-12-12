package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Horario;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Steven
 */
@Named(value = "adminClase")
@RequestScoped
public class AdminClaseMB {

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
    private String dia;

    @Getter
    @Setter
    private Date horaInicio;

    @Getter
    @Setter
    private Date horaFin;

    @Getter
    @Setter
    private List<Horario> horarios;

    @Getter
    @Setter
    private List<Horario> horariosSeleccionados;

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
    private List<Materia> materiasSeleccionadas;

    @Getter
    @Setter
    private int cantidadMaterias;

    @PostConstruct
    public void init() {
        this.materias = new ArrayList();
        this.dias = new ArrayList();
        this.horarios = new ArrayList();
        this.carreras = new ArrayList();
        llenarDias();
        limpiarPantalla();
        llenarCarreras();
    }

    public void limpiarPantalla() {
        this.materias = fachadaNegocio.getMaterias();
        this.cantidadMaterias = materias.size();
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

    public void agregarHorario() {
        Horario hor = new Horario();
        hor.setDia(this.dia);
        hor.setHorainicio(this.horaInicio);
        hor.setHorafin(this.horaFin);
        horarios.add(hor);
        cerrarCrearDialog();
    }

    public void eliminarHorarios() {
        for (Horario horarioElim : horariosSeleccionados) {
            horarios.remove(horarioElim);
        }
    }

    public void agregarClase() {
        System.out.println("IMpsfd ");
    }

    public void cerrarCrearDialog() {
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

}
