package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
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
@Named(value = "consultaMaterias")
@RequestScoped
public class ConsultaMateriasMB {
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;
   
    @Getter
    @Setter
    private List<Materia> materias;
    
    @Getter
    @Setter
    private List<Materia> materiasSeleccionadas;
    
    @Getter
    @Setter
    private List<String> carreras;
    
    
    public ConsultaMateriasMB() {
    }
    
    @PostConstruct
    public void init(){
        this.materias = fachadaNegocio.getMaterias();
        this.carreras = new ArrayList();
        llenarCarreras();
    }
    
    public void llenarCarreras(){
        List<Carrera> listaCarreras = fachadaNegocio.getCarreras();
        for (Carrera carrera : listaCarreras) {
            carreras.add(carrera.getNombre());
        }
    }
    
    public void consultarMateria(){
        for (Materia materia : materias) {
            System.out.println("-> "+materia.getNombre());            
        }
    }
    
    public void eliminarMaterias(){
        for (Materia materia : materiasSeleccionadas) {
            fachadaNegocio.eliminarMateria(materia);
        }
    }
    
}
