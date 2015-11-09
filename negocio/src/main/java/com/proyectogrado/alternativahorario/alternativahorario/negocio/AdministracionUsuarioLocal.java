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
    
    List<Usuario> buscarUsuario();

    Usuario getUsuario(final String id);
    
    Usuario getUsuarioPorNombre(String nombre);
    
    boolean iniciarSesion(Usuario usuario);
    
}
