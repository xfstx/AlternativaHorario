package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface AdministracionUsuarioLocal {
    
    List<Usuario> getUsuarios();

    boolean eliminarUsuario(Usuario usuario);

    List<Usuario> eliminarUsuarios(List<Usuario> usuarios);

    boolean agregarUsuario(Usuario usuario);

    boolean modificarUsuario(Usuario usuario);

    Usuario getUsuarioPorNombre(String nombre);
    
    boolean iniciarSesion(Usuario usuario);
    
}
