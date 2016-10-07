package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
@Named(value = "requisitos")
@ViewScoped
public class RequisitosMB implements Serializable {

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
    private List<Materia> materiasRequisitos;

    @Getter
    @Setter
    private Materia requisitoSeleccionado;

    @Getter
    @Setter
    private List<Materia> materiasAdd;

    @Getter
    @Setter
    private List<Materia> materiasAddSeleccionadas;

    @Getter
    @Setter
    private List<String> carreras;

    @Getter
    @Setter
    private boolean esAddRequisito;

    @PostConstruct
    public void init() {
        this.carreras = new ArrayList();
        limpiarPantalla();
    }

    public void limpiarPantalla() {
        this.esAddRequisito = false;
        this.materiaSeleccionada = null;
        this.materias = fachadaNegocio.getMaterias();
        this.materiasAdd = fachadaNegocio.getMaterias();
        llenarCarreras();
    }

    public void llenarCarreras() {
        List<Carrera> listaCarreras = fachadaNegocio.getCarreras();
        for (Carrera carrera : listaCarreras) {
            carreras.add(carrera.getNombre());
        }
    }

    public void cargarRequisitos() {
    }

    public void eliminarRequisito() {
    }

    public void agregarRequisito() {
        this.esAddRequisito = false;
    }
    
    public void upload() {
    }

}
