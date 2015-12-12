package com.proyectogrado.alternativahorario.alternativahorario.web;

import com.proyectogrado.alternativahorario.alternativahorario.negocio.FachadaNegocioLocal;
import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Steven
 */
@Named(value = "adminUsuario")
@RequestScoped
public class AdminUsuarioMB {

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

    @PostConstruct
    public void init() {
        limpiarConsulta();
        limpiarPantalla();
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

    public void limpiarPantalla() {
        this.usuario = "";
        this.contrasena = "";
        this.tipo = "";
    }

    public void agregarUsuario() {
        if (validarUsuario()) {
            Usuario usuario = new Usuario();
            usuario.setUsuario(this.usuario);
            usuario.setClave(this.contrasena);
            usuario.setTipo(this.tipo);

            boolean creacion = fachadaNegocio.agregarUsuario(usuario);
            if (creacion) {
                cerrarCrearDialog();
                limpiarPantalla();
                limpiarConsulta();
                notificarCreacionExitosa();
            } else {
                notificarCreacionFallida();
            }
        }
    }

    public void aceptarUsuario(Usuario usuario) {
    }

    public void abrirModificarUsuario(Usuario usuario) {
        System.out.println("Modificar Usuario " + usuario.getUsuario());
    }

    public boolean validarUsuario() {
        Usuario usuario = fachadaNegocio.getUsuarioPorNombre(this.usuario);
        if (usuario != null) {
            notificarUsuarioYaExiste();
            return false;
        }
        return true;
    }

    public void cerrarCrearDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('pnlAgregarUsuario').hide();");
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

    public void notificarNoSeleccion() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ERROR", "No ha seleccionado Usuarios a Eliminar");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionExitosa() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Se elimino el/los Usuario/s Exitosamente");
        Messages.addFlashGlobal(msg);
    }

    public void notificarEliminacionFallida() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "Hubo un error eliminando la Usuario");
        Messages.addFlashGlobal(msg);
    }

}
