package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Steven
 */
@Named(value = "index")
@RequestScoped
public class IndexMB implements Serializable{
    
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
