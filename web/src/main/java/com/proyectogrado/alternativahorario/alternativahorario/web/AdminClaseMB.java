package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Horario;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

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
    private List<String> materias;

    @Getter
    @Setter
    private String materia;

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

    @PostConstruct
    public void init() {
        this.materias = new ArrayList();
        this.dias = new ArrayList();
        this.horarios = new ArrayList();
        llenarMaterias();
        llenarDias();
    }

    public void limpiarConsulta() {

    }

    public void llenarMaterias() {
        List<Materia> materiasOrig = fachadaNegocio.getMaterias();
        for (Materia mat : materiasOrig) {
            this.materias.add(mat.getNombre());
        }
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
    }

    public void agregarClase() {
    }

}
