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
    public List<Usuario> buscarUsuario() {
        return fachadaPersistenciaUsuario.findAll();
    }

    @Override
    public Usuario getUsuario(String id) {
        return fachadaPersistenciaUsuario.find(id);
    }
    
    @Override
    public Usuario getUsuarioPorNombre(String nombre) {
        return fachadaPersistenciaUsuario.findByUsuario(nombre);
    }
 
    @Override
    public boolean iniciarSesion(Usuario usuario){
        Usuario login = fachadaPersistenciaUsuario.findByUsuarioClave(usuario.getUsuario(), usuario.getClave());
        if(login == null){
            return false;
        }
        return true;
    }
    
}
