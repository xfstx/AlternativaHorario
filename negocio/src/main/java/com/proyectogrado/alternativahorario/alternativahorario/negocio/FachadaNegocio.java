package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Usuario;
import com.proyectogrado.alternativahorario.persistencia.UsuarioFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Steven
 */
@Stateless
public class FachadaNegocio implements FachadaNegocioLocal {
    
    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    @Override
    public List<Usuario> buscarUsuario() {
        return usuarioFacade.findAll();
    }

    @Override
    public Usuario getUsuario(String id) {
        return usuarioFacade.find(id);
    }

    

}
