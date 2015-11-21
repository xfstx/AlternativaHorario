package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Clase;
import com.proyectogrado.alternativahorario.entidades.Materia;
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
@Named(value = "consultaClase")
@RequestScoped
public class ConsultaClaseMB {

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    List<String> materias;

    @Getter
    @Setter
    private String materia;

    @Getter
    @Setter
    private List<Clase> clases;

    @Getter
    @Setter
    private List<Clase> clasesSeleccionadas;

    @PostConstruct
    public void init() {
        this.materias = new ArrayList();
        llenarMaterias(); 
        limpiarConsulta();
    }

    public void limpiarConsulta() {

    }

    public void llenarMaterias() {
        List<Materia> materiasOrig = fachadaNegocio.getMaterias();
        for (Materia mat : materiasOrig) {
            this.materias.add(mat.getNombre());
        }
    }

    public void cargarClases() {
        this.clases = fachadaNegocio.getClasesPorMateria(buscarMateria(this.materia));
    }
    
    public Materia buscarMateria(String nombreMateria){
        return fachadaNegocio.getMateriaPorNombre(nombreMateria);
    }

}
