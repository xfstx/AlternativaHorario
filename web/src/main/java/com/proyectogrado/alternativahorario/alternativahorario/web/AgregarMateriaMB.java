package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.math.BigInteger;
import java.util.ArrayList;
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
@Named(value = "agregarMateria")
@RequestScoped
public class AgregarMateriaMB {

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

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
    private List<String> carreras;

    @Getter
    @Setter
    private int creditos;

    @Getter
    @Setter
    private int intesidadHoraria;

    @PostConstruct
    public void init() {
        this.carreras = new ArrayList();
        llenarCarreras();
    }

    public void llenarCarreras() {
        List<Carrera> listaCarreras = fachadaNegocio.getCarreras();
        for (Carrera carrera : listaCarreras) {
            carreras.add(carrera.getNombre());
        }
    }
    
    public void agregarMateria(){
        Materia materia = new Materia();
        materia.setNombre(nombre);
        // materia.setCarrera(carreras);
        materia.setSemestre(this.semestres);
        materia.setCreditos(this.creditos);
        materia.setIntensidadHoraria(this.intesidadHoraria);
        fachadaNegocio.agregarMateria(materia);
    }

}
