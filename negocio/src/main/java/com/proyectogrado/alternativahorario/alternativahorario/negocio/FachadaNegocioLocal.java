package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Steven
 */
@Local
public interface FachadaNegocioLocal {

    List<Usuario> buscarUsuario();

    Usuario getUsuario(final String id);
    
}
