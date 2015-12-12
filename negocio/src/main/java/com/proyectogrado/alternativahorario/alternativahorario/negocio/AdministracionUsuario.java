package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Usuario;
import com.proyectogrado.alternativahorario.persistencia.FachadaPersistenciaUsuarioLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class AdministracionUsuario implements AdministracionUsuarioLocal {

    @EJB
    private FachadaPersistenciaUsuarioLocal fachadaPersistenciaUsuario;

    @Override
    public List<Usuario> getUsuarios() {
        return fachadaPersistenciaUsuario.findAll();
    }

    @Override
    public boolean eliminarUsuario(Usuario usuario) {
        try {
            Usuario usuarioEliminar = fachadaPersistenciaUsuario.findByNombre(usuario.getUsuario());
            fachadaPersistenciaUsuario.remove(usuarioEliminar);
        } catch (Exception e) {
            System.out.println("Ocurrio un error eliminando la Usuario " + usuario.getUsuario());
            return false;
        }
        return true;
    }

    @Override
    public List<Usuario> eliminarUsuarios(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            eliminarUsuario(usuario);
        }
        return null;
    }

    @Override
    public boolean agregarUsuario(Usuario usuario) {
        try {
            fachadaPersistenciaUsuario.create(usuario);
        } catch (Exception e) {
            System.out.println("Ocurrio un error creacion la usuario " + usuario.getUsuario());
            return false;
        }
        return true;
    }

    @Override
    public boolean modificarUsuario(Usuario usuario) {
        try {
            fachadaPersistenciaUsuario.edit(usuario);
        } catch (Exception e) {
            System.out.println("Ocurrio un error modificacion la usuario " + usuario.getUsuario());
            return false;
        }
        return true;
    }

    @Override
    public Usuario getUsuarioPorNombre(String nombre) {
        return fachadaPersistenciaUsuario.findByNombre(nombre);    }

    @Override
    public boolean iniciarSesion(Usuario usuario) {
        Usuario login = fachadaPersistenciaUsuario.findByUsuarioClave(usuario.getUsuario(), usuario.getClave());
        if (login == null) {
            return false;
        }
        return true;
    }

}
