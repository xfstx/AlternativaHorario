package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.io.Serializable;
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
@Named(value = "agregarUsuario")
@ViewScoped
public class AgregarUsuarioMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private String usuario;

    @Getter
    @Setter
    private String clave;

    @Getter
    @Setter
    private String tipo;

    @PostConstruct
    public void init() {
        limpiarPantalla();
    }

    public void limpiarPantalla() {
        this.usuario = "";
        this.clave = "";
        this.tipo = "";
    }

    public void agregarUsuario() {
        if (validarUsuario()) {
            Usuario usuario = new Usuario();
            usuario.setUsuario(this.usuario);
            usuario.setClave(this.clave);
            usuario.setTipo(this.tipo);
            usuario.setEstado("REGISTRADO");
            boolean creacion = fachadaNegocio.agregarUsuario(usuario);
            if (creacion) {
                limpiarPantalla();
                notificarCreacionExitosa();
            } else {
                notificarCreacionFallida();
            }
        }
    }

    public boolean validarUsuario() {
        Usuario usuario = fachadaNegocio.getUsuarioPorNombre(this.usuario);
        if (usuario != null) {
            notificarUsuarioYaExiste();
            return false;
        }
        return true;
    }

    public void notificarCreacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Usuario Creado Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarUsuarioYaExiste() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "Ya existe una Usuario con este Nombre");
        Messages.addFlashGlobal(msg);
    }

    public void notificarCreacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error en la creacion de el Usuario");
        Messages.addFlashGlobal(msg);
    }

}
