package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.Cromosoma;
import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Horario;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Steven
 */
@Named(value = "alternativa")
@ViewScoped
public class AlternativaHorarioMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private List<Materia> materias;

    @Getter
    @Setter
    private Materia materiaSeleccionada;

    @Getter
    @Setter
    private List<Materia> materiasElegidas;

    @Getter
    @Setter
    private Materia materiaElegidaSeleccionada;

    @Getter
    @Setter
    private List<Cromosoma> resultado;

    @Getter
    @Setter
    private List<HorarioTable> horarios1;

    @Getter
    @Setter
    private List<HorarioTable> horarios2;

    @Getter
    @Setter
    private List<HorarioTable> horarios3;

    @Getter
    @Setter
    private List<HorarioTable> horarios4;

    @Getter
    @Setter
    private List<HorarioTable> horarios5;

    @Getter
    @Setter
    private List<HorarioTable> horarios6;

    @Getter
    @Setter
    private boolean visualizacion;

    @PostConstruct
    public void init() {
        this.visualizacion = true;
        this.materias = new ArrayList();
        this.materiasElegidas = new ArrayList();
        limpiarPantalla();
        llenarTablasHorarios();
        cargarHorariosResultado(); // TODO ELIM
    }

    public void limpiarPantalla() {
        this.materias = fachadaNegocio.getMaterias();
    }

    public void llenarTablasHorarios() {
        this.horarios1 = new ArrayList();
        this.horarios1 = llenarHorario();
        this.horarios2 = llenarHorario();
        this.horarios3 = llenarHorario();
        this.horarios4 = llenarHorario();
        this.horarios5 = llenarHorario();
        this.horarios6 = llenarHorario();
    }

    public List<HorarioTable> llenarHorario() {
        List<HorarioTable> horarios = new ArrayList();
        horarios.add(new HorarioTable(6, "06:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(7, "07:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(8, "08:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(9, "09:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(10, "10:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(11, "11:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(12, "12:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(13, "13:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(14, "14:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(15, "15:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(16, "16:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(17, "17:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(18, "18:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(19, "19:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(20, "20:00", "", "", "", "", "", ""));
        horarios.add(new HorarioTable(21, "21:00", "", "", "", "", "", ""));
        return horarios;
    }

    public void agregarMateria() {
        if (Objects.nonNull(materiaSeleccionada)) {
            if (materiasElegidas.contains(materiaSeleccionada)) {
                notificarMateriaYaAgregada();
            } else {
                materiasElegidas.add(materiaSeleccionada);
            }
        }
    }

    public void eliminarMateria() {
        if (Objects.nonNull(materiaElegidaSeleccionada)) {
            materiasElegidas.remove(materiaElegidaSeleccionada);
        }
    }

    public void cambio() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('statusDialog').show();");
        visualizacion = !visualizacion;
        context.execute("PF('statusDialog').hide();");
    }

    public void calcularAlternativa() {
        if (materiasElegidas.size() < 2) {
            notificarMateriasNoSeleccionadas();
        } else {
            cargarHorariosResultado();
        }
    }

    // TODO ELIIIM !!!
    public void cargarResultadoPruebaGraficaTODOELIM(){
        List<Clase> clasesPrueba = fachadaNegocio.getClases();
        List<Clase> clasesOrder = new ArrayList();
        this.resultado = new ArrayList();
        for (int i = 0; i < 3; i++) {
            clasesOrder.add(clasesPrueba.get(i));
        }
        for (int i = 0; i < 6; i++) {
            resultado.add(new Cromosoma(clasesOrder));
        }
    }
    
    public void cargarHorariosResultado() {
        cargarResultadoPruebaGraficaTODOELIM();
        //this.resultado = fachadaNegocio.calcularAlternativa(materiasElegidas);
        if (resultado.isEmpty()) {
            notificarNoResultado();
        } else {
            asignarHorarioGrafico(horarios1, resultado.get(0));
            asignarHorarioGrafico(horarios2, resultado.get(1));
            asignarHorarioGrafico(horarios3, resultado.get(2));
            asignarHorarioGrafico(horarios4, resultado.get(3));
            asignarHorarioGrafico(horarios5, resultado.get(4));
            asignarHorarioGrafico(horarios6, resultado.get(5));
        }
    }

    public void asignarHorarioGrafico(List<HorarioTable> horarios, Cromosoma resultado) {
        for (Clase clase : resultado.getClases()) {
            for (Horario horario : clase.getHorarioList()) {
                agregarClaseAHorarioGrafico(horarios, clase, horario);
            }
        }
    }

    public void agregarClaseAHorarioGrafico(List<HorarioTable> horarios, Clase clase, Horario horario) {
        int horaIniI = horario.getHorainicio().intValue() - 6;
        int horaFinI = horario.getHorafin().intValue() - 7;
        String dia = horario.getDia();
        switch (dia) {
            case "Lunes":
                horarios.get(horaIniI).setLunes(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                horarios.get(horaFinI).setLunes(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                break;

            case "Martes":
                horarios.get(horaIniI).setMartes(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                horarios.get(horaFinI).setMartes(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                break;

            case "Miercoles":
                horarios.get(horaIniI).setMiercoles(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                horarios.get(horaFinI).setMiercoles(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                break;

            case "Jueves":
                horarios.get(horaIniI).setJueves(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                horarios.get(horaFinI).setJueves(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                break;

            case "Viernes":
                horarios.get(horaIniI).setViernes(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                horarios.get(horaFinI).setViernes(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                break;

            case "Sabado":
                horarios.get(horaIniI).setSabado(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                horarios.get(horaFinI).setSabado(clase.getMateria().getNombre() + " - " + clase.getGrupo());
                break;
        }
    }

    public void notificarMateriaYaAgregada() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Esta materia ya esta agregada");
        Messages.addFlashGlobal(msg);
    }

    public void notificarMateriasNoSeleccionadas() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Debe seleccionar al menos 2 materias");
        Messages.addFlashGlobal(msg);
    }

    public void notificarNoResultado() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "No se pudo Calcular Horario para la Opcion Seleccionada");
        Messages.addFlashGlobal(msg);
    }

}
