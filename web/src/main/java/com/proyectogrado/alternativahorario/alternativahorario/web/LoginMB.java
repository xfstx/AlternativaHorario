package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Steven
 */
@Named(value = "login")
@RequestScoped
public class LoginMB implements Serializable{

    @EJB
    private FachadaNegocioLocal fachadaNegocio;
    
    @Getter
    @Setter
    private String usuario;
    
    @Getter
    @Setter
    private String clave;
    
    public String iniciarSesion(){
        Usuario user = new Usuario();
        user.setUsuario(this.usuario);
        user.setClave(this.clave);
        boolean ingreso = fachadaNegocio.iniciarSesion(user);
        if (ingreso) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Usuario o Clave Incorrectos"));
        }
        return null;        
    }

}
