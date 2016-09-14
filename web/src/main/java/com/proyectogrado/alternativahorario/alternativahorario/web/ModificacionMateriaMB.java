package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Materia;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

/**
 *
 * @author Steven
 */
@Named(value = "modificacionMateria")
@ViewScoped
public class ModificacionMateriaMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private Materia materiaSeleccionada;
    
    @Getter
    @Setter
    private List<String> carreras;
    
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
    private int creditos;

    @Getter
    @Setter
    private int intesidadHoraria;
    
    @PostConstruct
    public void init() {
        carreras = getCarreras();
    }

    public void cargarDatos() {
        materiaSeleccionada = fachadaNegocio.getMateriaPorNombre(nombre);
        semestres = materiaSeleccionada.getSemestre();
        carrera = materiaSeleccionada.getCarrera().getNombre();
        creditos = materiaSeleccionada.getCreditos();
        intesidadHoraria = materiaSeleccionada.getIntensidadHoraria();       
    }

    public Carrera buscarCarrera(String carrera) {
        return fachadaNegocio.getCarreraPorNombre(carrera);
    }
    
    public void lag(){
        System.out.println("laaaaaaaaaaaaaaag");
    }
    
    public void modificarMateria() {
        materiaSeleccionada.setCarrera(buscarCarrera(carrera));
        materiaSeleccionada.setCreditos(creditos);
        materiaSeleccionada.setIntensidadHoraria(intesidadHoraria);
        materiaSeleccionada.setSemestre(semestres);
        if (fachadaNegocio.modificarMateria(materiaSeleccionada)) {
            notificarModificacionExitosa();
        } else {
            notificarModificacionFallida();
        }
    }
    
    public void llenarCarreras() {
        List<Carrera> listaCarreras = fachadaNegocio.getCarreras();
        for (Carrera carrera : listaCarreras) {
            carreras.add(carrera.getNombre());
        }
    }
    
    public void notificarModificacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Se modifica la materia Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error al modificar la materia");
        Messages.addFlashGlobal(msg);
    }

}
