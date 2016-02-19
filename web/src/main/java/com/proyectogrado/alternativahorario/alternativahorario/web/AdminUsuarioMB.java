package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Steven
 */
@Named(value = "adminUsuario")
@ViewScoped
public class AdminUsuarioMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FachadaNegocioLocal fachadaNegocio;

    @Getter
    @Setter
    private List<Usuario> usuarios;

    @Getter
    @Setter
    private int cantidadUsuarios;

    @Getter
    @Setter
    private List<Usuario> usuariosSeleccionados;

    @Getter
    @Setter
    private String usuario;

    @Getter
    @Setter
    private String contrasena;

    @Getter
    @Setter
    private String tipo;

    @Getter
    @Setter
    private Usuario usuarioSeleccionado;

    @PostConstruct
    public void init() {
        limpiarConsulta();
        limpiarPantalla();
    }

    public void limpiarPantalla() {
        this.usuario = "";
        this.contrasena = "";
        this.tipo = "";
    }

    public void limpiarConsulta() {
        this.usuarios = fachadaNegocio.getUsuarios();
        this.cantidadUsuarios = usuarios.size();
    }

    public void eliminarUsuario(Usuario usuario) {
        boolean eliminar = fachadaNegocio.eliminarUsuario(usuario);
        if (eliminar) {
            limpiarConsulta();
            notificarEliminacionExitosa();
        } else {
            notificarEliminacionFallida();
        }
    }

    public void eliminarUsuarios() {
        if (validarUsuarios()) {
            fachadaNegocio.eliminarUsuarios(usuariosSeleccionados);
            limpiarConsulta();
            notificarEliminacionExitosa();
        }
    }

    public boolean validarUsuarios() {
        if (usuariosSeleccionados.isEmpty()) {
            notificarNoSeleccion();
            return false;
        }
        return true;
    }

    public void aceptarUsuario(Usuario usuario) {
        usuario.setEstado("ACTIVO");
        boolean modificarUsuario = fachadaNegocio.modificarUsuario(usuario);
        if (modificarUsuario) {
            notificarPerfilAceptadoExitosa();
        } else {
            notificarPerfilAceptadoFallida();
        }
    }

    public void abrirModificarUsuario(Usuario usuario) {
        this.usuarioSeleccionado = usuario;
        this.usuario = usuario.getUsuario();
        this.contrasena = usuario.getClave();
        this.tipo = usuario.getTipo();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlModificarUsuario').show();");
    }

    public void modificarUsuario() {
        this.usuarioSeleccionado.setUsuario(usuario);
        this.usuarioSeleccionado.setTipo(tipo);
        boolean modificarUsuario = fachadaNegocio.modificarUsuario(usuarioSeleccionado);
        if (modificarUsuario) {
            notificarModificacionExitosa();
        } else {
            notificarModificacionFallida();
        }
        cerrarModificarDialog();
    }

    public void cerrarModificarDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlModificarUsuario').hide();");
    }

    public void notificarNoSeleccion() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "No ha seleccionado Usuarios a Eliminar");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino el/los Usuario/s Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Hubo un error eliminando la Usuario");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Se modifico exitosamente el usuario");
        Messages.addFlashGlobal(msg);
    }

    public void notificarModificacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se pudo modificar el usuario");
        Messages.addFlashGlobal(msg);
    }

    public void notificarPerfilAceptadoExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Perfil Aceptado");
        Messages.addFlashGlobal(msg);
    }

    public void notificarPerfilAceptadoFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "No se pudo activar el perfil");
        Messages.addFlashGlobal(msg);
    }

}
