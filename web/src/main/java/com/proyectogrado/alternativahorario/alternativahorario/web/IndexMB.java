package com.proyectogrado.alternativahorario.alternativahorario.web;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Steven
 */
@Named(value = "index")
@ViewScoped
public class IndexMB implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /*
    public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            
            Usuario user = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
            if (user == null) {
                context.getExternalContext().redirect("permisos.xhtml");
            }
        } catch (Exception e) {
            System.out.println("Error :"+ e);
        }
    }
    */
}
