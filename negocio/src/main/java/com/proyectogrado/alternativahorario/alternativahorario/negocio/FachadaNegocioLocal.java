package com.proyectogrado.alternativahorario.alternativahorario.negocio;

import com.proyectogrado.alternativahorario.entidades.Carrera;
import com.proyectogrado.alternativahorario.entidades.Materia;
import com.proyectogrado.alternativahorario.entidades.Menu;
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
    
    Usuario getUsuarioPorNombre(String nombre);
    
    boolean iniciarSesion(Usuario usuario);
    
    List<Menu> getMenus();
    
    List<Materia> getMaterias();
    
    boolean eliminarMateria(Materia materia);
    
    void agregarMateria(Materia materia);
    
    List<Carrera> getCarreras();
    
}
