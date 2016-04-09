package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import java.io.Serializable;
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
@Named(value = "prueba")
@ViewScoped
public class PruebaMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private String grupo;
    
    @Getter
    @Setter
    private String profesor;
    
    @PostConstruct
    public void init() {
    }
    
    public void prueba(){
        System.out.println("This is a test... "+grupo+" - "+profesor);
    }

}
